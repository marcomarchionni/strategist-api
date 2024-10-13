package com.marcomarchionni.strategistapi.services.odata;

import java.util.List;

public interface FilterParser {
    List<ParsedFilter> parse(String filter);
}
