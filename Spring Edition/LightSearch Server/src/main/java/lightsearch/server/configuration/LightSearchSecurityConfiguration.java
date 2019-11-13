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

package lightsearch.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@ComponentScan
@EnableWebSecurity
@EnableScheduling
public class LightSearchSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${lightsearch.server.admin.username}")
    private String adminName;

    @Value("${lightsearch.server.admin.password}")
    private String password;

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername(adminName)
                .password(password)
                .roles("ADMIN").build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/login/clients",
                "/commands/type/client",
                "/static/**",
                "/favicon.ico",
                "/js/**",
                "/css/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/")
                        .authenticated()
                    .antMatchers(
                            "/session/admin",
                            "/commands/type/admin/",
                            "/commands/type/admin",
                            "/commands/type/admin?**")
                        .hasRole("ADMIN")
                .and()
                .formLogin()
                    .loginPage("/login/admin")
                    .permitAll()
                    .failureUrl("/login/admin?error")
                    .defaultSuccessUrl("/session/admin", true)
                .and()
                    .logout()
                    .logoutSuccessUrl("/login/admin");
    }
}
