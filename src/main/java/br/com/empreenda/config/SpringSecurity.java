package br.com.empreenda.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/").permitAll()
						.requestMatchers("/index/**").permitAll()
						.requestMatchers("/ajuda/**").permitAll()
						.requestMatchers("/register/**").permitAll()
						.requestMatchers("*/static/**").permitAll()
						.requestMatchers("/css/**").permitAll()
						.requestMatchers("/script/**").permitAll()
						.requestMatchers("/plugins/**").permitAll()
						.requestMatchers("/image/**").permitAll()
						.requestMatchers("/perfil/**").permitAll()
						.requestMatchers("/usuario/**").permitAll()
						.requestMatchers("/media/**").permitAll()
						.requestMatchers("/error/**").permitAll()
						.requestMatchers("/perfil").permitAll()
						.requestMatchers("/post/**").permitAll()
						.requestMatchers("/cursos/**").permitAll()
						.requestMatchers("/colab/**").hasRole("COLAB")
						.requestMatchers("/colaborador/**").hasRole("COLAB")
						.requestMatchers("/admin/**").hasRole("ADMIN")
				)
				.formLogin(form -> form.loginPage("/login")
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/index")
						.permitAll())
				.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll());

		return http.build();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

}
