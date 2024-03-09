package com.example.inventory.control.config;

public class OldConfigWebSecurity {
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new ShopmeUserDetailsService();
//    }
//
//    @Bean
//    public BCryptPasswordEncoder парольEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) выдает исключение {
//        http.authorizeRequests().antMatchers("/login").permitAll()
//                .antMatchers("/users/**", "/settings/**"). hasAuthority("Администратор")
//                .hasAnyAuthority("Администратор", "Редактор", "Продавец")
//                .hasAnyAuthority("Администратор", "Редактор", "Продавец", "Отправитель")
//                .anyRequest().authenticated()
//                .and ().formLogin()
//                .loginPage("/login")
//                .usernameParameter("email")
//                .permitAll()
//                .and()
//                .rememberMe().key("AbcdEfghIjklmNopQrsTuvXyz_0123456789")
//                .and()
//                .logout().permitAll ();
//
//        http.headers().frameOptions().sameOrigin();
//    }
//
//    @Override
//    public void configure(WebSecurity web) выдает исключение {
//        web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
//    }
}
