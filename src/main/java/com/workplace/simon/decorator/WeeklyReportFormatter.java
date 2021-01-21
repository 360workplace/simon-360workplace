package com.workplace.simon.decorator;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
@Service
public class WeeklyReportFormatter implements Formatter {
    @Override
    public Map format(List<Object[]> weeklyReport) {
        HashMap<String, HashMap<String, List<Object[]>>> result = new HashMap<>();
        String id = "";

        HashMap<String, List<Object[]>> elements = new HashMap<>();
        ArrayList<Object[]> curremt = new ArrayList<>();
        ArrayList<Object[]> history = new ArrayList<>();
        for (Object[] item : weeklyReport) {
            if (!id.equals(item[0].toString())) {
                ArrayList<Object[]> values = new ArrayList<>();
                elements = new HashMap<>();
                id = item[0].toString();
                result.put(id, elements);

                values.add(new Object[]{item[1], item[2], item[7], item[8], item[9], item[10], item[11]});

                curremt = new ArrayList<>();
                history = new ArrayList<>();
                elements.put("values", values);
                elements.put("current", curremt);
                elements.put("history", history);
            }

            if (item[5].toString().compareTo(item[3].toString()) >= 0 &&
                    item[5].toString().compareTo(item[4].toString()) <= 0
            ) {
                curremt.add(new Object[]{item[5], item[6]});
            } else {
                history.add(new Object[]{item[5], item[6]});
            }
        }

        return result;
    }
}
