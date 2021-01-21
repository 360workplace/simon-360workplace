package com.workplace.simon.decorator;

import java.util.List;
import java.util.Map;

public interface Formatter {
    Map format(List<Object[]> weeklyReport);
}
