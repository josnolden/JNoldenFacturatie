package nl.novi.jnoldenfacturatie.configuration;

import nl.novi.jnoldenfacturatie.filters.JwtRequestFilter;
import nl.novi.jnoldenfacturatie.services.GebruikerDataService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    public final GebruikerDataService gebruikerDataService;
    private final JwtRequestFilter jwtRequestFilter;

    public SpringSecurityConfiguration(GebruikerDataService gebruikerDataService, JwtRequestFilter jwtRequestFilter) {
        this.gebruikerDataService = gebruikerDataService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(gebruikerDataService);
        return new ProviderManager(auth);
    }

    @Bean
    protected SecurityFilterChain filter (HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                                auth
//                .requestMatchers("/**").permitAll()
                                        .requestMatchers(HttpMethod.GET,"/gebruikers").hasAnyRole("gebruiker", "beheerder")
                                        .requestMatchers(HttpMethod.POST, "/gebruikers").permitAll()
                                        .requestMatchers(HttpMethod.PUT,"/gebruikers/**").hasRole("beheerder")
                                        .requestMatchers(HttpMethod.DELETE, "/gebruikers/**").hasRole("beheerder")
                                        .requestMatchers(HttpMethod.GET,"/artikelen").hasAnyRole("gebruiker", "beheerder")
                                        .requestMatchers(HttpMethod.POST, "/artikelen").hasRole("beheerder")
                                        .requestMatchers(HttpMethod.PUT, "/artikelen/**").hasRole("beheerder")
                                        .requestMatchers(HttpMethod.DELETE, "/artikelen/**").hasRole("beheerder")
                                        .requestMatchers(HttpMethod.GET,"/klanten").hasAnyRole("gebruiker", "beheerder")
                                        .requestMatchers(HttpMethod.POST, "/klanten").hasRole("beheerder")
                                        .requestMatchers(HttpMethod.PUT, "/klanten/**").hasRole("beheerder")
                                        .requestMatchers(HttpMethod.DELETE, "/klanten/**").hasRole("beheerder")
                                        .requestMatchers(HttpMethod.GET,"/facturen").hasAnyRole("gebruiker", "beheerder")
                                        .requestMatchers(HttpMethod.POST, "/facturen").hasAnyRole("gebruiker", "beheerder")
                                        .requestMatchers(HttpMethod.PUT, "/facturen/**").hasAnyRole("gebruiker", "beheerder")
                                        .requestMatchers(HttpMethod.DELETE, "/facturen/**").hasRole("beheerder")
                                        .requestMatchers("/login").permitAll()
                                        .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
