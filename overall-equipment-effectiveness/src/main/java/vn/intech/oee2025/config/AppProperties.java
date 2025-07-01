package vn.intech.oee2025.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ConfigurationProperties(prefix = "appsettings")
@Configuration("app")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppProperties {
	private String guidPath;
}
