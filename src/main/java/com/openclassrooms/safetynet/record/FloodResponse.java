package com.openclassrooms.safetynet.record;

import java.util.List;
import java.util.Map;

public record FloodResponse(Map<String, List<FireResponse>> households // key = address, value = personnes
) {
}
