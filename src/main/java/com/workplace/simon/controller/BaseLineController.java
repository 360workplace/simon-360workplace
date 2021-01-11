package com.workplace.simon.controller;

import com.workplace.simon.model.Source;
import com.workplace.simon.model.Resource;
import com.workplace.simon.model.SourceType;
import com.workplace.simon.model.User;
import com.workplace.simon.service.AreaService;
import com.workplace.simon.service.SourceService;
import com.workplace.simon.service.RegisterService;
import com.workplace.simon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/data/")
public class BaseLineController {
    @Autowired
    private SourceService sourceService;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

    public SourceService getSourceService() {
        return sourceService;
    }

    @Deprecated
    public RegisterService getRegisterService() {
        return registerService;
    }

    public UserService getUserService() {
        return userService;
    }

    public AreaService getAreaService() {
        return areaService;
    }

    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

    @GetMapping("baseline/{userId}")
    public String showBaselineForm(
            @PathVariable("userId") Long userId,
            Model model
    ) {
        Source baseLine = new Source();
        baseLine.getResources().add(new Resource());
        baseLine.setType(SourceType.BASE_LINE);
        baseLine.setUserId(userId);
        User currentUser = this.getUserService().findById(baseLine.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + baseLine.getUserId()));

        model.addAttribute("baseLine", baseLine);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allUsers", this.getUserService().findAll());
        model.addAttribute("userid", userId);

        return "baseline-form";
    }

    @PostMapping(params = "save", path = {"add", "add/{userId}"})
    public String addBaseline(
            @PathVariable("userId") Long userId,
            @Valid Source baseLine,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            User currentUser = this.getUserService().findById(baseLine.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + baseLine.getUserId()));
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("allUsers", this.getUserService().findAll());
            model.addAttribute("userid", userId);

            return "baseline-form";
        }

        this.getSourceService().save(baseLine);

        return "redirect:/";
    }

    @RequestMapping(params = "addItem", path = {"add", "add/{userId}"})
    public String addRow(
            @PathVariable("userId") Long userId,
            Source baseLine,
            HttpServletRequest request
    ) {
        baseLine.getResources().add(new Resource());

        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "baseline-form::#items";
        } else {
            return "baseline-form";
        }
    }

    @RequestMapping(params = "removeItem", path = {"add", "add/{userId}"})
    public String removeRow(
            @PathVariable("userId") Long userId,
            final Source baseLine,
            @RequestParam("removeItem") int index,
            HttpServletRequest request
    ) {
        baseLine.getResources().remove(index);

        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "baseline-form::#items";
        } else {
            return "baseline-form";
        }
    }

    @PostMapping("update/{id}")
    public String updateBaseline(
            @PathVariable("id") Long id,
            @Valid Source baseLine,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            baseLine.setId(id);
            User currentUser = this.getUserService().findById(baseLine.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + baseLine.getUserId()));
            model.addAttribute("currentUser", currentUser);
            model.addAttribute("allUsers", this.getUserService().findAll());

            return "baseline-form";
        }

        this.getSourceService().save(baseLine);

        return "redirect:/data/source/list/" + baseLine.getType();
    }

    @GetMapping("source/list/{type}")
    public String showSourceList(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("type") SourceType type,
            @RequestParam("areaFilter") Optional<Long> area,
            Model model
    ) {
        setCurrentUser(userDetails, model);
        Long areaId = area.orElse(0L);
        model.addAttribute("allAreas", this.getAreaService().findAll());

        if (areaId == 0L) {
            model.addAttribute("baseLine", this.getSourceService().findByType(type));
        } else {
            model.addAttribute("baseLine", this.getSourceService().findByTypeAndArea(type, areaId));
        }

        model.addAttribute("type", type);

        return "baseline-list";
    }

    @GetMapping("baseline/show/{baseLineId}")
    public String showItem(
            @PathVariable("baseLineId") Long baseLineId,
            Model model
    ) {
        Source baseLine = this.getSourceService().findById(baseLineId)
                .orElseThrow(() -> new IllegalArgumentException("The id to gets the baseline record is not exists."));
        User currentUser = this.getUserService().findById(baseLine.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id : " + baseLine.getUserId()));

        model.addAttribute("baseLine", baseLine);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allUsers", this.getUserService().findAll());

        return "baseline-show";
    }

    @GetMapping("baseline/trunk/{baseLineId}")
    public String moveToTrunk(
            @PathVariable("baseLineId") Long baseLineId,
            Model model
    ) {
        Source baseLine = this.getSourceService().findById(baseLineId)
                .orElseThrow(() -> new IllegalArgumentException("The id to gets the baseline record is not exists."));
        baseLine.setActive(false);
        this.getSourceService().save(baseLine);

        return "redirect:/data/source/list/" + baseLine.getType();
    }

    private User setCurrentUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = this.getUserService().findByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);

        return currentUser;
    }
}
