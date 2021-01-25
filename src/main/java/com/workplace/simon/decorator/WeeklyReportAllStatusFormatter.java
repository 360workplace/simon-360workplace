package com.workplace.simon.decorator;

import com.workplace.simon.model.Period;
import com.workplace.simon.service.UtilDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WeeklyReportAllStatusFormatter implements Formatter {
    @Autowired
    private UtilDate utilDate;

    public UtilDate getUtilDate() {
        return utilDate;
    }

    @Override
    public List format(List<Object[]> weeklyReport) {
        ArrayList<HashMap<String, List<Object[]>>> result = new ArrayList<>();
        String id = "";
        Period currentPeriod = this.getUtilDate().getPeriod();

        HashMap<String, List<Object[]>> elements = new HashMap<>();
        ArrayList<Object[]> curremt = new ArrayList<>();
        ArrayList<Object[]> history = new ArrayList<>();
        for (Object[] item : weeklyReport) {
            if (!id.equals(item[0].toString())) {
                ArrayList<Object[]> values = new ArrayList<>();
                elements = new HashMap<>();
                id = item[0].toString();
                result.add(elements);

                values.add(new Object[]{item[1], item[2], item[7], item[8], item[9], item[10], item[11]});

                curremt = new ArrayList<>();
                history = new ArrayList<>();
                elements.put("values", values);
                elements.put("current", curremt);
                elements.put("history", history);
            }

            if (item[5] == null || item[3] == null || item[4] == null) continue;

            if (item[5].toString().compareTo(currentPeriod.getStartDate().toString()) >= 0 &&
                    item[5].toString().compareTo(currentPeriod.getEndDate().toString()) <= 0
            ) {
                curremt.add(new Object[]{item[5], item[6]});
            } else {
                history.add(new Object[]{item[5], item[6]});
            }
        }

        return result;
    }
}
