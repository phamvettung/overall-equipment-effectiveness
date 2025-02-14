package vn.intech.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ConfigurationProperties(prefix = "appsettings")
@Configuration("appinfor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppInfor {
	private String version;
	private String tutorialPath;
}
