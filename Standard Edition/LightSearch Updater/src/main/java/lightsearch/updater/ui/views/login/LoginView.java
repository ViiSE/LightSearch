/*
 *  Copyright 2019 ViiSE
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package lightsearch.updater.ui.views.login;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;

@Tag("login-view")
@Route(value=LoginView.ROUTE)
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver{

    public static final String ROUTE = "login";

    private LoginOverlay login = new LoginOverlay();

    public LoginView() { //AuthenticationManager authenticationManager, CustomRequestCache requestCache) {
        login.setAction("login");
        login.setOpened(true);
        login.setTitle("LightSearch Updater");
        login.setDescription("Sign in, please");
        login.setForgotPasswordButtonVisible(false);
        super.getElement().appendChild(login.getElement());
//        super.add(login);
//        login.addLoginListener(e -> {
//            try {
//                final Authentication authentication = authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(e.getUsername(), e.getPassword()));
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                login.close();
//                UI.getCurrent().navigate(requestCache.resolveRedirectUrl());
//            } catch(AuthenticationException ex) {
//                login.setError(true);
//            }
//        });
        //super.add(login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(!event.getLocation().getQueryParameters().getParameters().getOrDefault("error", Collections.emptyList()).isEmpty()) {
            login.setError(true);
        }
    }
}
