package com.megazone.devops.comment;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.megazone.devops.common.security.CommonPrincipal;
import com.megazone.devops.common.security.jwt.JwtAuthenticationProperties;
import com.megazone.devops.common.security.jwt.filter.JwtAuthenticationFilter;
import com.megazone.devops.common.web.Application;
import com.megazone.devops.common.web.WebAutoconfigure;
import com.megazone.devops.common.web.resolver.CommonPrincipalHandlerMethodArgumentResolver;

/**
 * Comment Autoconfigure
 * @author sudden(sch0718@naver.com)
 */
@Configuration
@AutoConfigureAfter(Application.class)
@AutoConfigureBefore(WebAutoconfigure.class)
@ComponentScan(basePackages = CommentAutoconfigure.BASE_PACKAGE_PREFIX)
@EntityScan(basePackageClasses = Jsr310JpaConverters.class, basePackages = { CommentAutoconfigure.BASE_PACKAGE_PREFIX })
@EnableJpaRepositories(basePackages = CommentAutoconfigure.BASE_PACKAGE_PREFIX)
@EnableJpaAuditing(auditorAwareRef = "anonymousAuditorAware")
public class CommentAutoconfigure extends WebSecurityConfigurerAdapter implements WebMvcConfigurer, InitializingBean {

	class AnonymousAuditAware implements AuditorAware<String> {

		@Override
		public Optional<String> getCurrentAuditor() {
			CommonPrincipal principal = (CommonPrincipal) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Optional<String> optional = Optional.ofNullable(principal.getId());
			optional.orElse("anonymous");
			return optional;
		}

	}

	private static final Logger LOG = LoggerFactory.getLogger(CommentAutoconfigure.class);

	public static final String BASE_PACKAGE_PREFIX = "com.megazone.devops.comment";

	@Autowired
	JwtAuthenticationProperties jwtConfigs;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (LOG.isDebugEnabled())
			LOG.debug("{} initialized.", getClass().getName());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().logout().disable().formLogin().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
				.authenticationEntryPoint(
						(request, response, e) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.and().authorizeRequests().anyRequest().authenticated().and()
				.addFilterBefore(new JwtAuthenticationFilter(authenticationManagerBean(), jwtConfigs),
						UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new CommonPrincipalHandlerMethodArgumentResolver());
	}

	/**
	 * Entity Audititor 객체 생성
	 * @return
	 */
	@Bean
	AuditorAware<String> anonymousAuditorAware() {
		return new AnonymousAuditAware();
	}
}
