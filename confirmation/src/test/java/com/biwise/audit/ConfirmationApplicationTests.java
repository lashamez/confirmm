package com.biwise.audit;

import com.biwise.audit.domain.dto.PackageDto;
import com.biwise.audit.domain.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConfirmationApplicationTests {

	@Test
	void contextLoads() {
		PackageDto packageDto = new PackageDto();
		UserDto userDto = new UserDto();
		userDto.setCurrentPlan(packageDto);
	}

}
