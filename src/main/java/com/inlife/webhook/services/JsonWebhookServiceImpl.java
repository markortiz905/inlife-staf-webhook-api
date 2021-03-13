package com.inlife.webhook.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inlife.webhook.dto.StaffRequestDto;
import com.inlife.webhook.entities.Staff;
import com.inlife.webhook.exception.BadRequestServiceException;
import com.inlife.webhook.exception.ServiceException;
import com.inlife.webhook.repositories.jpa.JsonWebhookRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Primary
@Service
public class JsonWebhookServiceImpl implements JsonWebhookService {

    @Autowired
    private JsonWebhookRepository jsonWebhookRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    @Async
    public CompletableFuture<Void> saveClientAsync(StaffRequestDto request, @NonNull String rawJson) throws ServiceException, BadRequestServiceException {
        return CompletableFuture.supplyAsync(() -> {
            Long id = request.getItem_id();
            Staff staff = new Staff();
            staff.setId(id);
            request.getFields().parallelStream().forEach( field -> {
                try {
                    if (field.getLabel().equalsIgnoreCase("Name (relationship)")) {
                        String rel = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("item_id")).collect(Collectors.joining(","));
                        staff.setName(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Employee ID")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        staff.setEmployeeId(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Status")) {
                        String status = (String)((Map<String, Object>)field.getValues().get(0).getValue()).get("text");
                        staff.setStatus(status);
                    } else if (field.getLabel().equalsIgnoreCase("Pay Grade")) {
                        String paygrade = (String)((Map<String, Object>)field.getValues().get(0).getValue()).get("text");
                        staff.setPaygrade(paygrade);
                    } else if (field.getLabel().equalsIgnoreCase("photo")) {
                        String photo = (String)((Map<String, Object>)field.getValues().get(0).getValue()).get("file_id");
                        staff.setPhoto(photo);
                    } else if (field.getLabel().equalsIgnoreCase("Gender")) {
                        String gender = (String)((Map<String, Object>)field.getValues().get(0).getValue()).get("text");
                        staff.setGender(gender);
                    } else if (field.getLabel().equalsIgnoreCase("Date of birth")) {
                        String dob = field.getValues().get(0).getStart_date();
                        staff.setDateOfBirth(dob);
                    } else if (field.getLabel().equalsIgnoreCase("Location")) { //might be multiple based on the picture from G
                        String location = (String)((Map<String, Object>)field.getValues().get(0).getValue()).get("text");
                        staff.setLocation(location);
                    } else if (field.getLabel().equalsIgnoreCase("Personality description")) {
                        String desc = (String)field.getValues().get(0).getValue();
                        staff.setPersonalityDescription(desc);
                    } else if (field.getLabel().equalsIgnoreCase("Interests, hobbies and activities")) {
                        String interests = (String)field.getValues().get(0).getValue();
                        staff.setInterestsHobbiesAndActivities(interests);
                    } else if (field.getLabel().equalsIgnoreCase("Disability experience")) {
                        String experience = (String)field.getValues().get(0).getValue();
                        staff.setDisabilitiesExperience(experience);
                    } else if (field.getLabel().equalsIgnoreCase("Why choose disability")) {
                        String why = (String)field.getValues().get(0).getValue();
                        staff.setWhyChooseDisability(why);
                    } else if (field.getLabel().equalsIgnoreCase("Disability type experience")) {
                        String rel = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setDisabilityTypeExperience(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Client age group preference")) {
                        String rel = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setClientAgeGroupPreference(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Client gender preference")) {
                        String genderPreference = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setClientGenderPreference(genderPreference);
                    } else if (field.getLabel().equalsIgnoreCase("Access to a car")) {
                        String accessToCar = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setAccessToCar(accessToCar);
                    } else if (field.getLabel().equalsIgnoreCase("Willing to provide transport")) {
                        String willingToProvideTransport = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setWillingToProvideTransport(willingToProvideTransport);
                    } else if (field.getLabel().equalsIgnoreCase("Assistant profile status")) {
                        String assistanceProfileStatus = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setAssistanceProfileStatus(assistanceProfileStatus);
                    } else if (field.getLabel().equalsIgnoreCase("Workable record")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getValue().toString()).collect(Collectors.joining(","));
                        staff.setWorkableRecord(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Looking for work")) {
                        String lookingForWork = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setLookingForWork(lookingForWork);
                    } else if (field.getLabel().equalsIgnoreCase("Extra hours sought")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getValue().toString()).collect(Collectors.joining(","));
                        staff.setExtraHoursSought(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("[old] Early morning shifts")) {
                        String earlyMorningShift = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setEarlyMorningShift(earlyMorningShift);
                    } else if (field.getLabel().equalsIgnoreCase("[old] Early morning shift pref")) {
                        String earlyMorningShiftPref = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setEarlyMorningShiftPref(earlyMorningShiftPref);
                    } else if (field.getLabel().equalsIgnoreCase("[old] School hour shifts")) {
                        String schoolHoursShift = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setSchoolHoursShift(schoolHoursShift);
                    } else if (field.getLabel().equalsIgnoreCase("[old] School hours shift pref")) {
                        String schoolHoursShiftPref = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setSchoolHoursShiftPref(schoolHoursShiftPref);
                    } else if (field.getLabel().equalsIgnoreCase("[old] Evening shifts")) {
                        String eveningShifts = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setEveningShifts(eveningShifts);
                    } else if (field.getLabel().equalsIgnoreCase("[old] Evening shift pref")) {
                        String eveningShiftsPref = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setEveningShiftsPref(eveningShiftsPref);
                    } else if (field.getLabel().equalsIgnoreCase("[old] Sleepover shifts")) {
                        String sleepOverShifts = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setSleepOverShifts(sleepOverShifts);
                    } else if (field.getLabel().equalsIgnoreCase("[old] Sleepover shift pref")) {
                        String sleepOverShiftsPref = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setSleepOverShiftsPref(sleepOverShiftsPref);
                    } else if (field.getLabel().equalsIgnoreCase("[old] Other staffing preferences")) {
                        String otherStaffingPrefs = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setOtherStaffingPrefs(otherStaffingPrefs);
                    } else if (field.getLabel().equalsIgnoreCase("responded")) {
                        String responded = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setResponded(responded);
                    } else if (field.getLabel().equalsIgnoreCase("Date Availability was last updated")) {
                        String dateAvailabilityWasLastUpdated = field.getValues().parallelStream().map( val -> val.getStart()).collect(Collectors.joining(","));
                        staff.setDateAvailabilityWasLastUpdated(dateAvailabilityWasLastUpdated);
                    } else if (field.getLabel().equalsIgnoreCase("Send reminder")) {
                        String sendReminder = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setSendReminder(sendReminder);
                    } else if (field.getLabel().equalsIgnoreCase("File notes")) {
                        String fileNotes = field.getValues().parallelStream().map( val -> val.getValue().toString()).collect(Collectors.joining(","));
                        staff.setFileNotes(fileNotes);
                    } else if (field.getLabel().equalsIgnoreCase("Onboarding complete")) {
                        String onboardingComplete = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setOnboardingComplete(onboardingComplete);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Online induction complete")) {
                        String onlineInductionComplete = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setOnlineInductionComplete(onlineInductionComplete);
                    } else if (field.getLabel().equalsIgnoreCase("induction-end-date")) {
                        String inductionEndDate = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setInductionEndDate(inductionEndDate);
                    } else if (field.getLabel().equalsIgnoreCase("Requested by")) {
                        String requestedBy = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("item_id")).collect(Collectors.joining(","));
                        staff.setRequestedBy(requestedBy);
                    } else if (field.getLabel().equalsIgnoreCase("Date requested")) {
                        String dateRequested = field.getValues().parallelStream().map( val -> val.getStart_date()).collect(Collectors.joining(","));
                        staff.setDateRequested(dateRequested);
                    } else if (field.getLabel().equalsIgnoreCase("Ref check 1")) {
                        String refCheckOne = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setRefCheckOne(refCheckOne);
                    } else if (field.getLabel().equalsIgnoreCase("Ref check 2")) {
                        String refCheckTwo = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setRefCheckTwo(refCheckTwo);
                    } else if (field.getLabel().equalsIgnoreCase("Interview")) {
                        String interview = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setInterview(interview);
                    } else if (field.getLabel().equalsIgnoreCase("Empl. contract")) {
                        String employeeContract = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setEmployeeContract(employeeContract);
                    } else if (field.getLabel().equalsIgnoreCase("DWES")) {
                        String dwes = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        staff.setDwes(dwes);
                    } else if (field.getLabel().equalsIgnoreCase("Police check")) {
                        String policeCheck = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        staff.setPoliceCheck(policeCheck);
                    } else if (field.getLabel().equalsIgnoreCase("First Aid cert")) {
                        String firstAidCert = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        staff.setFirstAidCert(firstAidCert);
                    } else if (field.getLabel().equalsIgnoreCase("CPR")) {
                        String cpr = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        staff.setCpr(cpr);
                    } else if (field.getLabel().equalsIgnoreCase("Manual handling")) {
                        String manualHandling = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        staff.setManualHandling(manualHandling);
                    }
                } catch (Exception e) {log.error(e.getMessage());}
            });
            return staff;
        }).thenAccept(result -> {
            log.info("saving client -> " + result.getId() + " ");
            result.setJsonSource(rawJson);
            Staff saved = jsonWebhookRepository.save(result);
            log.info("client entry saved with id : " + saved.getId());
        }).exceptionally( e -> { e.printStackTrace(); return null;}).thenRun(() -> log.info("finished saving client entry"));
    }

}
