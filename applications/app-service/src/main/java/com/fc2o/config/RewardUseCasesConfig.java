package com.fc2o.config;

import com.fc2o.model.reward.gateways.RewardRepository;
import com.fc2o.usecase.reward.business.RegisterRewardUseCase;
import com.fc2o.usecase.reward.crud.CreateRewardUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "com.fc2o.usecase.reward",
  includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")},
  useDefaultFilters = false
)
public class RewardUseCasesConfig {

  @Bean
  public RegisterRewardUseCase registerRewardUseCase(CreateRewardUseCase createRewardUseCase) {
    return new RegisterRewardUseCase(createRewardUseCase);
  }

  @Bean
  public CreateRewardUseCase createRewardUseCase(RewardRepository rewardRepository) {
    return new CreateRewardUseCase(rewardRepository);
  }
}
