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
                        // Public endpoints - everyone can access authentication and public endpoints
                        .requestMatchers("/api/v2/auth/**", "/public/**").permitAll()

                        // Full access for only MANAGER to CRUD all resources (including crops)
                        .requestMatchers( "/api/v2/manage/**","/api/v2/log/**").hasAnyAuthority("MANAGER")
                        .requestMatchers("/api/v2/email/**").hasAnyAuthority("ADMINISTRATIVE","MANAGER" ,"SCIENTIST")
//                        .requestMatchers("/api/v2/staff/**",  "/api/v2/field/**").hasAnyAuthority("MANAGER", "ADMINISTRATIVE")

                        // ADMINISTRATIVE users can only GET /api/v2/crop/** (view crop data)
                        .requestMatchers(HttpMethod.GET, "/api/v2/crop/**").hasAnyAuthority("ADMINISTRATIVE","MANAGER","SCIENTIST")

                        // Restrict SCIENTIST AND MANAGER users from performing POST, PUT, DELETE on /api/v2/crop/** (they can only view with GET)
                        .requestMatchers(HttpMethod.POST, "/api/v2/crop/**").hasAnyAuthority("MANAGER","SCIENTIST")
                        .requestMatchers(HttpMethod.PUT, "/api/v2/crop/**").hasAnyAuthority("MANAGER","SCIENTIST")
                        .requestMatchers(HttpMethod.DELETE, "/api/v2/crop/**").hasAnyAuthority("MANAGER","SCIENTIST")
                        // ADMINISTRATIVE users can only GET /api/v2/crop/** (view crop data)


                        .requestMatchers(HttpMethod.GET, "/api/v2/field/**").hasAnyAuthority("ADMINISTRATIVE","MANAGER","SCIENTIST")
                        // Restrict SCIENTIST AND MANGER users from performing POST, PUT, DELETE on /api/v2/crop/** (they can only view with GET)
                        .requestMatchers(HttpMethod.POST, "/api/v2/field/**").hasAnyAuthority("MANAGER","SCIENTIST")
                        .requestMatchers(HttpMethod.PUT, "/api/v2/field/**").hasAnyAuthority("MANAGER","SCIENTIST")
                        .requestMatchers(HttpMethod.DELETE, "/api/v2/field/**").hasAnyAuthority("MANAGER","SCIENTIST")


                        .requestMatchers(HttpMethod.GET, "/api/v2/vehicles/**").hasAnyAuthority("ADMINISTRATIVE","MANAGER","SCIENTIST")
                        // Restrict ADMINISTRATIVE AND MANAGER  from performing POST, PUT, DELETE on /api/v2/crop/** (they can only view with GET)
                        .requestMatchers(HttpMethod.POST, "/api/v2/vehicles/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.PUT, "/api/v2/vehicles/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v2/vehicles/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")

                        .requestMatchers(HttpMethod.GET, "/api/v2/staff/**").hasAnyAuthority("ADMINISTRATIVE","MANAGER","SCIENTIST")
                        // Restrict ADMINISTRATIVE AND MANAGER  from performing POST, PUT, DELETE on /api/v2/crop/** (they can only view with GET)
                        .requestMatchers(HttpMethod.POST, "/api/v2/staff/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.PUT, "/api/v2/staff/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v2/staff/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")


                        .requestMatchers(HttpMethod.GET, "/api/v2/equipment/**").hasAnyAuthority("ADMINISTRATIVE","MANAGER","SCIENTIST")
                        // Restrict ADMINISTRATIVE AND MANAGER  from performing POST, PUT, DELETE on /api/v2/crop/** (they can only view with GET)
                        .requestMatchers(HttpMethod.POST, "/api/v2/equipment/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.PUT, "/api/v2/equipment/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")
                        .requestMatchers(HttpMethod.DELETE, "/api/v2/equipment/**").hasAnyAuthority("MANAGER","ADMINISTRATIVE")





                        // Any other request must be authenticated (no public access allowed)
                        .anyRequest().authenticated())

                // Stateless session management (no HTTP sessions)
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Add authentication provider and JWT filter
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
