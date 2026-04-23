package com.fc2o.usecase.user;

import com.fc2o.usecase.UseCase;

public enum UserUseCases implements UseCase {
  VALIDATE_PERMISSIONS,
  REGISTER_TOURNAMENT,
  BLOCK_TICKET,
  BAN_USER,
  UNBAN_USER
}
