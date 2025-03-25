package com.fc2o.usecase.tournament;

import com.fc2o.usecase.UseCase;

public enum TournamentUseCases implements UseCase {

  //Ticket Use Case
  BLOCK_TICKET,

  //Disqualify participant Use Case
  DISQUALIFY_PARTICIPANT,

  //Add Moderator Use Case
  ADD_MOD,

  //Start Use Case
  START,
  FORCE_START,

  //Pre-Register Participant in Tournament Use Case
  FORCE_PREREGISTER_PARTICIPANT,
  PREREGISTER_PARTICIPANT,

  //Register Participant in Tournament Use Case
  REGISTER_PARTICIPANT,
  FORCE_REGISTER_PARTICIPANT,

  //Cancel Use Case
  CANCEL,

  //Finalize Use Case
  FINALIZE,
  FORCE_FINALIZE,

  //Reschedule Use Case
  RESCHEDULE,
  FORCE_RESCHEDULE,
  RESCHEDULE_END_DATE,

  // Match
  REGISTER_MATCH,
  START_MATCH,
  FORCE_START_MATCH,
  FINALIZE_MATCH,
  CANCEL_MATCH
}
