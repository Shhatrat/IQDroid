package com.shhatrat.iqdroid.iqData

import com.google.gson.Gson
import com.shhatrat.iqdroid.model.WebBody
import fi.iki.elonen.NanoHTTPD

class Web(port: Int) : NanoHTTPD(port) {

    private var webpage = ""

    fun setData(data: String){
        webpage = data
    }

    fun setData(data: WebBody){
        webpage = Gson().toJson(data)
//        webpage = "{\n" +
//                "  \"id\": 1567367825494,\n" +
//                "  \"req\": [\n" +
//                "    \"BATTERY\",\n" +
//                "    \"GPS\",\n" +
//                "    \"TIME\"\n" +
//                "  ],\n" +
//                "  \"screens\": [\n" +
//                "    {\n" +
//                "      \"id\": 1,\n" +
//                "      \"navigation\": [\n" +
//                "        {\n" +
//                "          \"id\": 2,\n" +
//                "          \"keyCode\": 8,\n" +
//                "          \"transition\": \"\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"id\": 2,\n" +
//                "          \"keyCode\": 13,\n" +
//                "          \"transition\": \"\"\n" +
//                "        }\n" +
//                "      ],\n" +
//                "      \"screenItemList\": [\n" +
//                "        {\n" +
//                "          \"type\": \"BACKGROUND\",\n" +
//                "          \"color\": 16776960,\n" +
//                "          \"backgroundColor\": 16776960\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"type\": \"DRAW_TEXT\",\n" +
//                "          \"x\": 50,\n" +
//                "          \"y\": 50,\n" +
//                "          \"color\": 16711680,\n" +
//                "          \"backgroundColor\": 16776960,\n" +
//                "          \"data\": {\n" +
//                "            \"font\": 4,\n" +
//                "            \"text\": \"testBLEEEd\",\n" +
//                "            \"justification\": 1\n" +
//                "          }\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"id\": 2,\n" +
//                "      \"navigation\": [\n" +
//                "        {\n" +
//                "          \"id\": 1,\n" +
//                "          \"keyCode\": 8,\n" +
//                "          \"transition\": \"\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"id\": 1,\n" +
//                "          \"keyCode\": 13,\n" +
//                "          \"transition\": \"\"\n" +
//                "        }\n" +
//                "      ],\n" +
//                "      \"screenItemList\": [\n" +
//                "        {\n" +
//                "          \"type\": \"DRAW_TEXT\",\n" +
//                "          \"x\": 55,\n" +
//                "          \"y\": 55,\n" +
//                "          \"color\": 16711680,\n" +
//                "          \"backgroundColor\": 16776960,\n" +
//                "          \"data\": {\n" +
//                "            \"font\": 4,\n" +
//                "            \"text\": \"test XD\",\n" +
//                "            \"justification\": 1\n" +
//                "          }\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"id\": 3,\n" +
//                "      \"navigation\": [\n" +
//                "        {\n" +
//                "          \"id\": 1,\n" +
//                "          \"keyCode\": 8,\n" +
//                "          \"transition\": \"\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"id\": 1,\n" +
//                "          \"keyCode\": 13,\n" +
//                "          \"transition\": \"\"\n" +
//                "        }\n" +
//                "      ],\n" +
//                "      \"screenItemList\": [\n" +
//                "        {\n" +
//                "          \"type\": \"DRAW_TEXT\",\n" +
//                "          \"x\": 55,\n" +
//                "          \"y\": 55,\n" +
//                "          \"color\": 16711680,\n" +
//                "          \"backgroundColor\": 16776960,\n" +
//                "          \"data\": {\n" +
//                "            \"font\": 4,\n" +
//                "            \"text\": \"test XD\",\n" +
//                "            \"justification\": 1\n" +
//                "          }\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"id\": 3,\n" +
//                "      \"navigation\": [\n" +
//                "        {\n" +
//                "          \"id\": 1,\n" +
//                "          \"keyCode\": 8,\n" +
//                "          \"transition\": \"\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"id\": 1,\n" +
//                "          \"keyCode\": 13,\n" +
//                "          \"transition\": \"\"\n" +
//                "        }\n" +
//                "      ],\n" +
//                "      \"screenItemList\": [\n" +
//                "        {\n" +
//                "          \"type\": \"DRAW_TEXT\",\n" +
//                "          \"x\": 55,\n" +
//                "          \"y\": 55,\n" +
//                "          \"color\": 16711680,\n" +
//                "          \"backgroundColor\": 16776960,\n" +
//                "          \"data\": {\n" +
//                "            \"font\": 4,\n" +
//                "            \"text\": \"test XD\",\n" +
//                "            \"justification\": 1\n" +
//                "          }\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"id\": 3,\n" +
//                "      \"navigation\": [\n" +
//                "        {\n" +
//                "          \"id\": 1,\n" +
//                "          \"keyCode\": 8,\n" +
//                "          \"transition\": \"\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"id\": 1,\n" +
//                "          \"keyCode\": 13,\n" +
//                "          \"transition\": \"\"\n" +
//                "        }\n" +
//                "      ],\n" +
//                "      \"screenItemList\": [\n" +
//                "        {\n" +
//                "          \"type\": \"DRAW_TEXT\",\n" +
//                "          \"x\": 55,\n" +
//                "          \"y\": 55,\n" +
//                "          \"color\": 16711680,\n" +
//                "          \"backgroundColor\": 16776960,\n" +
//                "          \"data\": {\n" +
//                "            \"font\": 4,\n" +
//                "            \"text\": \"test XD\",\n" +
//                "            \"justification\": 1\n" +
//                "          }\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"id\": 3,\n" +
//                "      \"navigation\": [\n" +
//                "        {\n" +
//                "          \"id\": 1,\n" +
//                "          \"keyCode\": 8,\n" +
//                "          \"transition\": \"\"\n" +
//                "        },\n" +
//                "        {\n" +
//                "          \"id\": 1,\n" +
//                "          \"keyCode\": 13,\n" +
//                "          \"transition\": \"\"\n" +
//                "        }\n" +
//                "      ],\n" +
//                "      \"screenItemList\": [\n" +
//                "        {\n" +
//                "          \"type\": \"DRAW_TEXT\",\n" +
//                "          \"x\": 55,\n" +
//                "          \"y\": 55,\n" +
//                "          \"color\": 16711680,\n" +
//                "          \"backgroundColor\": 16776960,\n" +
//                "          \"data\": {\n" +
//                "            \"font\": 4,\n" +
//                "            \"text\": \"test XD\",\n" +
//                "            \"justification\": 1\n" +
//                "          }\n" +
//                "        }\n" +
//                "      ]\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}"
    }

    override fun serve(session: IHTTPSession): Response {
        return newFixedLengthResponse(webpage)
    }
}