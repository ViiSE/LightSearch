/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.controller;

import lightsearch.server.log.LoggerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static lightsearch.server.log.LogMessageTypeEnum.INFO;

@Controller
@RequestMapping("/login")
public class AdminLoginController {

    @Autowired
    private LoggerServer logger;

    @GetMapping(value = "/admin")
    public String login(Model model, @RequestParam(value = "error", required = false) String error) {
        if(error != null)
            model.addAttribute("loginError", true);
        return "login";
    }
}
