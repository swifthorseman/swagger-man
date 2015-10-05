package com.swifthorseman.transpiler.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.swifthorseman.transpiler.domain.enumeration.DeviceStatusIdentifierEnum;

import com.swifthorseman.transpiler.domain.enumeration.P2PEComplianceFlagEnum;

import com.swifthorseman.transpiler.domain.enumeration.DeviceOwnershipFlagEnum;

/**
 * A PhysicalDevice.
 */
@Entity
@Table(name = "PHYSICAL_DEVICE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PhysicalDevice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "device_model_identifier")
    private String deviceModelIdentifier;
    
    @Column(name = "device_model_name")
    private String deviceModelName;
    
    @Column(name = "serial_number")
    private String serialNumber;
    
    @Column(name = "payment_capture_device_identifier")
    private String paymentCaptureDeviceIdentifier;
    
    @Column(name = "gate_way_identifier")
    private String gateWayIdentifier;
    
    @Column(name = "party_level")
    private String partyLevel;
    
    @Column(name = "applied_to_party_identifier")
    private String appliedToPartyIdentifier;
    
    @Column(name = "party_role_identifier")
    private String partyRoleIdentifier;
    
    @Column(name = "party_role_name")
    private String partyRoleName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "device_status_identifier")
    private DeviceStatusIdentifierEnum deviceStatusIdentifier;
    
    @Column(name = "device_firmware_version")
    private String deviceFirmwareVersion;
    
    @Column(name = "device_manufacturer_identifier")
    private String deviceManufacturerIdentifier;
    
    @Column(name = "device_manufacturer_name")
    private String deviceManufacturerName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "p2_pecompliance_flag")
    private P2PEComplianceFlagEnum P2PEComplianceFlag;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "device_ownership_flag")
    private DeviceOwnershipFlagEnum deviceOwnershipFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceModelIdentifier() {
        return deviceModelIdentifier;
    }

    public void setDeviceModelIdentifier(String deviceModelIdentifier) {
        this.deviceModelIdentifier = deviceModelIdentifier;
    }

    public String getDeviceModelName() {
        return deviceModelName;
    }

    public void setDeviceModelName(String deviceModelName) {
        this.deviceModelName = deviceModelName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPaymentCaptureDeviceIdentifier() {
        return paymentCaptureDeviceIdentifier;
    }

    public void setPaymentCaptureDeviceIdentifier(String paymentCaptureDeviceIdentifier) {
        this.paymentCaptureDeviceIdentifier = paymentCaptureDeviceIdentifier;
    }

    public String getGateWayIdentifier() {
        return gateWayIdentifier;
    }

    public void setGateWayIdentifier(String gateWayIdentifier) {
        this.gateWayIdentifier = gateWayIdentifier;
    }

    public String getPartyLevel() {
        return partyLevel;
    }

    public void setPartyLevel(String partyLevel) {
        this.partyLevel = partyLevel;
    }

    public String getAppliedToPartyIdentifier() {
        return appliedToPartyIdentifier;
    }

    public void setAppliedToPartyIdentifier(String appliedToPartyIdentifier) {
        this.appliedToPartyIdentifier = appliedToPartyIdentifier;
    }

    public String getPartyRoleIdentifier() {
        return partyRoleIdentifier;
    }

    public void setPartyRoleIdentifier(String partyRoleIdentifier) {
        this.partyRoleIdentifier = partyRoleIdentifier;
    }

    public String getPartyRoleName() {
        return partyRoleName;
    }

    public void setPartyRoleName(String partyRoleName) {
        this.partyRoleName = partyRoleName;
    }

    public DeviceStatusIdentifierEnum getDeviceStatusIdentifier() {
        return deviceStatusIdentifier;
    }

    public void setDeviceStatusIdentifier(DeviceStatusIdentifierEnum deviceStatusIdentifier) {
        this.deviceStatusIdentifier = deviceStatusIdentifier;
    }

    public String getDeviceFirmwareVersion() {
        return deviceFirmwareVersion;
    }

    public void setDeviceFirmwareVersion(String deviceFirmwareVersion) {
        this.deviceFirmwareVersion = deviceFirmwareVersion;
    }

    public String getDeviceManufacturerIdentifier() {
        return deviceManufacturerIdentifier;
    }

    public void setDeviceManufacturerIdentifier(String deviceManufacturerIdentifier) {
        this.deviceManufacturerIdentifier = deviceManufacturerIdentifier;
    }

    public String getDeviceManufacturerName() {
        return deviceManufacturerName;
    }

    public void setDeviceManufacturerName(String deviceManufacturerName) {
        this.deviceManufacturerName = deviceManufacturerName;
    }

    public P2PEComplianceFlagEnum getP2PEComplianceFlag() {
        return P2PEComplianceFlag;
    }

    public void setP2PEComplianceFlag(P2PEComplianceFlagEnum P2PEComplianceFlag) {
        this.P2PEComplianceFlag = P2PEComplianceFlag;
    }

    public DeviceOwnershipFlagEnum getDeviceOwnershipFlag() {
        return deviceOwnershipFlag;
    }

    public void setDeviceOwnershipFlag(DeviceOwnershipFlagEnum deviceOwnershipFlag) {
        this.deviceOwnershipFlag = deviceOwnershipFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhysicalDevice physicalDevice = (PhysicalDevice) o;

        if ( ! Objects.equals(id, physicalDevice.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PhysicalDevice{" +
                "id=" + id +
                ", deviceModelIdentifier='" + deviceModelIdentifier + "'" +
                ", deviceModelName='" + deviceModelName + "'" +
                ", serialNumber='" + serialNumber + "'" +
                ", paymentCaptureDeviceIdentifier='" + paymentCaptureDeviceIdentifier + "'" +
                ", gateWayIdentifier='" + gateWayIdentifier + "'" +
                ", partyLevel='" + partyLevel + "'" +
                ", appliedToPartyIdentifier='" + appliedToPartyIdentifier + "'" +
                ", partyRoleIdentifier='" + partyRoleIdentifier + "'" +
                ", partyRoleName='" + partyRoleName + "'" +
                ", deviceStatusIdentifier='" + deviceStatusIdentifier + "'" +
                ", deviceFirmwareVersion='" + deviceFirmwareVersion + "'" +
                ", deviceManufacturerIdentifier='" + deviceManufacturerIdentifier + "'" +
                ", deviceManufacturerName='" + deviceManufacturerName + "'" +
                ", P2PEComplianceFlag='" + P2PEComplianceFlag + "'" +
                ", deviceOwnershipFlag='" + deviceOwnershipFlag + "'" +
                '}';
    }
}
