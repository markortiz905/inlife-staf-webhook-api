package com.inlife.webhook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author mark ortiz
 */
@Entity
@Table(name = "staff")
@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"jsonSource"})
public class Staff {
    @Id
    @EqualsAndHashCode.Include
    private Long id; //item_id

    private String name;

    @Column(columnDefinition = "text")
    private String employeeId;
    @Column(columnDefinition = "text")
    private String status;
    @Column(columnDefinition = "text")
    private String paygrade;
    @Column(columnDefinition = "text")
    private String photo;
    @Column(columnDefinition = "text")
    private String gender;
    @Column(columnDefinition = "text")
    private String dateOfBirth;
    @Column(columnDefinition = "text")
    private String location;
    @Column(columnDefinition = "text")
    private String personalityDescription;
    @Column(columnDefinition = "text")
    private String interestsHobbiesAndActivities;
    @Column(columnDefinition = "text")
    private String disabilitiesExperience;
    @Column(columnDefinition = "text")
    private String languagesSpoken;
    @Column(columnDefinition = "text")
    private String whyChooseDisability;
    @Column(columnDefinition = "text")
    private String disabilityTypeExperience;
    @Column(columnDefinition = "text")
    private String clientAgeGroupPreference;
    @Column(columnDefinition = "text")
    private String clientGenderPreference;
    @Column(columnDefinition = "text")
    private String accessToCar;
    @Column(columnDefinition = "text")
    private String willingToProvideTransport;
    @Column(columnDefinition = "text")
    private String assistanceProfileStatus;
    @Column(columnDefinition = "text")
    private String workableRecord;
    @Column(columnDefinition = "text")
    private String lookingForWork;
    @Column(columnDefinition = "text")
    private String extraHoursSought;

    @Column(columnDefinition = "text")
    private String earlyMorningShift;
    @Column(columnDefinition = "text")
    private String earlyMorningShiftPref;
    @Column(columnDefinition = "text")
    private String schoolHoursShift;
    @Column(columnDefinition = "text")
    private String schoolHoursShiftPref;
    @Column(columnDefinition = "text")
    private String eveningShifts;
    @Column(columnDefinition = "text")
    private String eveningShiftsPref;
    @Column(columnDefinition = "text")
    private String sleepOverShifts;
    @Column(columnDefinition = "text")
    private String sleepOverShiftsPref;
    @Column(columnDefinition = "text")
    private String otherStaffingPrefs;

    @Column(columnDefinition = "text")
    private String responded;
    @Column(columnDefinition = "text")
    private String dateAvailabilityWasLastUpdated;
    @Column(columnDefinition = "text")
    private String sendReminder;
    @Column(columnDefinition = "text")
    private String fileNotes;
    @Column(columnDefinition = "text")
    private String onboardingComplete;
    @Column(columnDefinition = "text")
    private String onlineInductionComplete;
    @Column(columnDefinition = "text")
    private String inductionEndDate;
    @Column(columnDefinition = "text")
    private String requestedBy;
    @Column(columnDefinition = "text")
    private String dateRequested;
    @Column(columnDefinition = "text")
    private String refCheckOne;
    @Column(columnDefinition = "text")
    private String refCheckTwo;
    private String interview;
    @Column(columnDefinition = "text")
    private String employeeContract;
    @Column(columnDefinition = "text")
    private String dwes;
    @Column(columnDefinition = "text")
    private String policeCheck;
    @Column(columnDefinition = "text")
    private String firstAidCert;
    @Column(columnDefinition = "text")
    private String cpr;
    @Column(columnDefinition = "text")
    private String manualHandling;

    @Column(name = "jsonSource", columnDefinition = "json")
    @JsonRawValue
    @JsonIgnore
    private String jsonSource;

}
