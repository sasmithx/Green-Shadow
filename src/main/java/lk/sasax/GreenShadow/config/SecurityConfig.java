package lk.sasax.GreenShadow.config;


import lk.sasax.GreenShadow.service.impl.UserServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserServiceIMPL ourUserDetailsService;
    @Autowired
    private JWTFilter jwtAuthFIlter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request

                        .requestMatchers("/api/v1/auth/**", "/public/**").permitAll()

                        .requestMatchers( "/api/v1/manage/**","/api/v1/log/**").hasAnyAuthority("MANAGER")

                        .requestMatchers(HttpMethod.GET, "/api/v1/crop/**").hasAnyAuthority("ADMINISTRATIVE","MANAGER","SCIENTIST")
                        .requestMatchers(HttpMethod.POST, "/api/v1/crop/**").hasAnyAuthority("MANAGER","SCIENTIST")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/crop/**").hasAnyAuthority("MANAGER","SCIENTIST")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/crop/**").hasAnyAuthority("MANAGER","SCIENTIST")

                        .requestMatchers(HttpMethod.GET, "/api/v1/field/**").hasAnyAuthority("ADMINISTRATIVE","MANAGER","SCIENTIST")
                        .requestMatchers(HttpMethod.POST, "/api/v1/field/**").hasAnyAuthority("MANAGER","SCIENTIST")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/field/**").hasAnyAuthority("MANAGER","SCIENTIST")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/field/**").hasAnyAuthority("MANAGER","SCIENTIST")

                        .requestMatchers(HttpMethod.GET, "/api/v1/vehicles/**").hasAnyAuthority("ADMINISTRATIVE","MANAGER","SCIENTIST")
                        .requestMatchers(HttpMethod.POST, "/api/v1/vehicles/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/vehicles/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/vehicles/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")

                        .requestMatchers(HttpMethod.GET, "/api/v1/staff/**").hasAnyAuthority("ADMINISTRATIVE","MANAGER","SCIENTIST")
                        .requestMatchers(HttpMethod.POST, "/api/v1/staff/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/staff/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/staff/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")

                        .requestMatchers(HttpMethod.GET, "/api/v1/equipment/**").hasAnyAuthority("ADMINISTRATIVE","MANAGER","SCIENTIST")
                        .requestMatchers(HttpMethod.POST, "/api/v1/equipment/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/equipment/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/equipment/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")

                        .requestMatchers(HttpMethod.GET, "/api/v1/crop_detail/**").hasAnyAuthority("ADMINISTRATIVE","MANAGER","SCIENTIST")
                        .requestMatchers(HttpMethod.POST, "/api/v1/crop_detail/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/crop_detail/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/crop_detail/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")


                        .anyRequest().authenticated())

                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFIlter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(ourUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
