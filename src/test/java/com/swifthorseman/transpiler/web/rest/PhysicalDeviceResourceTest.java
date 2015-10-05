package com.swifthorseman.transpiler.web.rest;

import com.swifthorseman.transpiler.Application;
import com.swifthorseman.transpiler.domain.PhysicalDevice;
import com.swifthorseman.transpiler.repository.PhysicalDeviceRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.swifthorseman.transpiler.domain.enumeration.DeviceStatusIdentifierEnum;
import com.swifthorseman.transpiler.domain.enumeration.P2PEComplianceFlagEnum;
import com.swifthorseman.transpiler.domain.enumeration.DeviceOwnershipFlagEnum;

/**
 * Test class for the PhysicalDeviceResource REST controller.
 *
 * @see PhysicalDeviceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PhysicalDeviceResourceTest {

    private static final String DEFAULT_DEVICE_MODEL_IDENTIFIER = "SAMPLE_TEXT";
    private static final String UPDATED_DEVICE_MODEL_IDENTIFIER = "UPDATED_TEXT";
    private static final String DEFAULT_DEVICE_MODEL_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_DEVICE_MODEL_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_SERIAL_NUMBER = "SAMPLE_TEXT";
    private static final String UPDATED_SERIAL_NUMBER = "UPDATED_TEXT";
    private static final String DEFAULT_PAYMENT_CAPTURE_DEVICE_IDENTIFIER = "SAMPLE_TEXT";
    private static final String UPDATED_PAYMENT_CAPTURE_DEVICE_IDENTIFIER = "UPDATED_TEXT";
    private static final String DEFAULT_GATE_WAY_IDENTIFIER = "SAMPLE_TEXT";
    private static final String UPDATED_GATE_WAY_IDENTIFIER = "UPDATED_TEXT";
    private static final String DEFAULT_PARTY_LEVEL = "SAMPLE_TEXT";
    private static final String UPDATED_PARTY_LEVEL = "UPDATED_TEXT";
    private static final String DEFAULT_APPLIED_TO_PARTY_IDENTIFIER = "SAMPLE_TEXT";
    private static final String UPDATED_APPLIED_TO_PARTY_IDENTIFIER = "UPDATED_TEXT";
    private static final String DEFAULT_PARTY_ROLE_IDENTIFIER = "SAMPLE_TEXT";
    private static final String UPDATED_PARTY_ROLE_IDENTIFIER = "UPDATED_TEXT";
    private static final String DEFAULT_PARTY_ROLE_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_PARTY_ROLE_NAME = "UPDATED_TEXT";

    private static final DeviceStatusIdentifierEnum DEFAULT_DEVICE_STATUS_IDENTIFIER = DeviceStatusIdentifierEnum.WPStock;
    private static final DeviceStatusIdentifierEnum UPDATED_DEVICE_STATUS_IDENTIFIER = DeviceStatusIdentifierEnum.custAllocated;
    private static final String DEFAULT_DEVICE_FIRMWARE_VERSION = "SAMPLE_TEXT";
    private static final String UPDATED_DEVICE_FIRMWARE_VERSION = "UPDATED_TEXT";
    private static final String DEFAULT_DEVICE_MANUFACTURER_IDENTIFIER = "SAMPLE_TEXT";
    private static final String UPDATED_DEVICE_MANUFACTURER_IDENTIFIER = "UPDATED_TEXT";
    private static final String DEFAULT_DEVICE_MANUFACTURER_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_DEVICE_MANUFACTURER_NAME = "UPDATED_TEXT";

    private static final P2PEComplianceFlagEnum DEFAULT_P2_PECOMPLIANCE_FLAG = P2PEComplianceFlagEnum.Y;
    private static final P2PEComplianceFlagEnum UPDATED_P2_PECOMPLIANCE_FLAG = P2PEComplianceFlagEnum.N;

    private static final DeviceOwnershipFlagEnum DEFAULT_DEVICE_OWNERSHIP_FLAG = DeviceOwnershipFlagEnum.W;
    private static final DeviceOwnershipFlagEnum UPDATED_DEVICE_OWNERSHIP_FLAG = DeviceOwnershipFlagEnum.C;

    @Inject
    private PhysicalDeviceRepository physicalDeviceRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPhysicalDeviceMockMvc;

    private PhysicalDevice physicalDevice;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PhysicalDeviceResource physicalDeviceResource = new PhysicalDeviceResource();
        ReflectionTestUtils.setField(physicalDeviceResource, "physicalDeviceRepository", physicalDeviceRepository);
        this.restPhysicalDeviceMockMvc = MockMvcBuilders.standaloneSetup(physicalDeviceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        physicalDevice = new PhysicalDevice();
        physicalDevice.setDeviceModelIdentifier(DEFAULT_DEVICE_MODEL_IDENTIFIER);
        physicalDevice.setDeviceModelName(DEFAULT_DEVICE_MODEL_NAME);
        physicalDevice.setSerialNumber(DEFAULT_SERIAL_NUMBER);
        physicalDevice.setPaymentCaptureDeviceIdentifier(DEFAULT_PAYMENT_CAPTURE_DEVICE_IDENTIFIER);
        physicalDevice.setGateWayIdentifier(DEFAULT_GATE_WAY_IDENTIFIER);
        physicalDevice.setPartyLevel(DEFAULT_PARTY_LEVEL);
        physicalDevice.setAppliedToPartyIdentifier(DEFAULT_APPLIED_TO_PARTY_IDENTIFIER);
        physicalDevice.setPartyRoleIdentifier(DEFAULT_PARTY_ROLE_IDENTIFIER);
        physicalDevice.setPartyRoleName(DEFAULT_PARTY_ROLE_NAME);
        physicalDevice.setDeviceStatusIdentifier(DEFAULT_DEVICE_STATUS_IDENTIFIER);
        physicalDevice.setDeviceFirmwareVersion(DEFAULT_DEVICE_FIRMWARE_VERSION);
        physicalDevice.setDeviceManufacturerIdentifier(DEFAULT_DEVICE_MANUFACTURER_IDENTIFIER);
        physicalDevice.setDeviceManufacturerName(DEFAULT_DEVICE_MANUFACTURER_NAME);
        physicalDevice.setP2PEComplianceFlag(DEFAULT_P2_PECOMPLIANCE_FLAG);
        physicalDevice.setDeviceOwnershipFlag(DEFAULT_DEVICE_OWNERSHIP_FLAG);
    }

    @Test
    @Transactional
    public void createPhysicalDevice() throws Exception {
        int databaseSizeBeforeCreate = physicalDeviceRepository.findAll().size();

        // Create the PhysicalDevice

        restPhysicalDeviceMockMvc.perform(post("/api/physicalDevices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(physicalDevice)))
                .andExpect(status().isCreated());

        // Validate the PhysicalDevice in the database
        List<PhysicalDevice> physicalDevices = physicalDeviceRepository.findAll();
        assertThat(physicalDevices).hasSize(databaseSizeBeforeCreate + 1);
        PhysicalDevice testPhysicalDevice = physicalDevices.get(physicalDevices.size() - 1);
        assertThat(testPhysicalDevice.getDeviceModelIdentifier()).isEqualTo(DEFAULT_DEVICE_MODEL_IDENTIFIER);
        assertThat(testPhysicalDevice.getDeviceModelName()).isEqualTo(DEFAULT_DEVICE_MODEL_NAME);
        assertThat(testPhysicalDevice.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testPhysicalDevice.getPaymentCaptureDeviceIdentifier()).isEqualTo(DEFAULT_PAYMENT_CAPTURE_DEVICE_IDENTIFIER);
        assertThat(testPhysicalDevice.getGateWayIdentifier()).isEqualTo(DEFAULT_GATE_WAY_IDENTIFIER);
        assertThat(testPhysicalDevice.getPartyLevel()).isEqualTo(DEFAULT_PARTY_LEVEL);
        assertThat(testPhysicalDevice.getAppliedToPartyIdentifier()).isEqualTo(DEFAULT_APPLIED_TO_PARTY_IDENTIFIER);
        assertThat(testPhysicalDevice.getPartyRoleIdentifier()).isEqualTo(DEFAULT_PARTY_ROLE_IDENTIFIER);
        assertThat(testPhysicalDevice.getPartyRoleName()).isEqualTo(DEFAULT_PARTY_ROLE_NAME);
        assertThat(testPhysicalDevice.getDeviceStatusIdentifier()).isEqualTo(DEFAULT_DEVICE_STATUS_IDENTIFIER);
        assertThat(testPhysicalDevice.getDeviceFirmwareVersion()).isEqualTo(DEFAULT_DEVICE_FIRMWARE_VERSION);
        assertThat(testPhysicalDevice.getDeviceManufacturerIdentifier()).isEqualTo(DEFAULT_DEVICE_MANUFACTURER_IDENTIFIER);
        assertThat(testPhysicalDevice.getDeviceManufacturerName()).isEqualTo(DEFAULT_DEVICE_MANUFACTURER_NAME);
        assertThat(testPhysicalDevice.getP2PEComplianceFlag()).isEqualTo(DEFAULT_P2_PECOMPLIANCE_FLAG);
        assertThat(testPhysicalDevice.getDeviceOwnershipFlag()).isEqualTo(DEFAULT_DEVICE_OWNERSHIP_FLAG);
    }

    @Test
    @Transactional
    public void getAllPhysicalDevices() throws Exception {
        // Initialize the database
        physicalDeviceRepository.saveAndFlush(physicalDevice);

        // Get all the physicalDevices
        restPhysicalDeviceMockMvc.perform(get("/api/physicalDevices"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(physicalDevice.getId().intValue())))
                .andExpect(jsonPath("$.[*].deviceModelIdentifier").value(hasItem(DEFAULT_DEVICE_MODEL_IDENTIFIER.toString())))
                .andExpect(jsonPath("$.[*].deviceModelName").value(hasItem(DEFAULT_DEVICE_MODEL_NAME.toString())))
                .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].paymentCaptureDeviceIdentifier").value(hasItem(DEFAULT_PAYMENT_CAPTURE_DEVICE_IDENTIFIER.toString())))
                .andExpect(jsonPath("$.[*].gateWayIdentifier").value(hasItem(DEFAULT_GATE_WAY_IDENTIFIER.toString())))
                .andExpect(jsonPath("$.[*].partyLevel").value(hasItem(DEFAULT_PARTY_LEVEL.toString())))
                .andExpect(jsonPath("$.[*].appliedToPartyIdentifier").value(hasItem(DEFAULT_APPLIED_TO_PARTY_IDENTIFIER.toString())))
                .andExpect(jsonPath("$.[*].partyRoleIdentifier").value(hasItem(DEFAULT_PARTY_ROLE_IDENTIFIER.toString())))
                .andExpect(jsonPath("$.[*].partyRoleName").value(hasItem(DEFAULT_PARTY_ROLE_NAME.toString())))
                .andExpect(jsonPath("$.[*].deviceStatusIdentifier").value(hasItem(DEFAULT_DEVICE_STATUS_IDENTIFIER.toString())))
                .andExpect(jsonPath("$.[*].deviceFirmwareVersion").value(hasItem(DEFAULT_DEVICE_FIRMWARE_VERSION.toString())))
                .andExpect(jsonPath("$.[*].deviceManufacturerIdentifier").value(hasItem(DEFAULT_DEVICE_MANUFACTURER_IDENTIFIER.toString())))
                .andExpect(jsonPath("$.[*].deviceManufacturerName").value(hasItem(DEFAULT_DEVICE_MANUFACTURER_NAME.toString())))
                .andExpect(jsonPath("$.[*].P2PEComplianceFlag").value(hasItem(DEFAULT_P2_PECOMPLIANCE_FLAG.toString())))
                .andExpect(jsonPath("$.[*].deviceOwnershipFlag").value(hasItem(DEFAULT_DEVICE_OWNERSHIP_FLAG.toString())));
    }

    @Test
    @Transactional
    public void getPhysicalDevice() throws Exception {
        // Initialize the database
        physicalDeviceRepository.saveAndFlush(physicalDevice);

        // Get the physicalDevice
        restPhysicalDeviceMockMvc.perform(get("/api/physicalDevices/{id}", physicalDevice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(physicalDevice.getId().intValue()))
            .andExpect(jsonPath("$.deviceModelIdentifier").value(DEFAULT_DEVICE_MODEL_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.deviceModelName").value(DEFAULT_DEVICE_MODEL_NAME.toString()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.paymentCaptureDeviceIdentifier").value(DEFAULT_PAYMENT_CAPTURE_DEVICE_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.gateWayIdentifier").value(DEFAULT_GATE_WAY_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.partyLevel").value(DEFAULT_PARTY_LEVEL.toString()))
            .andExpect(jsonPath("$.appliedToPartyIdentifier").value(DEFAULT_APPLIED_TO_PARTY_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.partyRoleIdentifier").value(DEFAULT_PARTY_ROLE_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.partyRoleName").value(DEFAULT_PARTY_ROLE_NAME.toString()))
            .andExpect(jsonPath("$.deviceStatusIdentifier").value(DEFAULT_DEVICE_STATUS_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.deviceFirmwareVersion").value(DEFAULT_DEVICE_FIRMWARE_VERSION.toString()))
            .andExpect(jsonPath("$.deviceManufacturerIdentifier").value(DEFAULT_DEVICE_MANUFACTURER_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.deviceManufacturerName").value(DEFAULT_DEVICE_MANUFACTURER_NAME.toString()))
            .andExpect(jsonPath("$.P2PEComplianceFlag").value(DEFAULT_P2_PECOMPLIANCE_FLAG.toString()))
            .andExpect(jsonPath("$.deviceOwnershipFlag").value(DEFAULT_DEVICE_OWNERSHIP_FLAG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPhysicalDevice() throws Exception {
        // Get the physicalDevice
        restPhysicalDeviceMockMvc.perform(get("/api/physicalDevices/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhysicalDevice() throws Exception {
        // Initialize the database
        physicalDeviceRepository.saveAndFlush(physicalDevice);

		int databaseSizeBeforeUpdate = physicalDeviceRepository.findAll().size();

        // Update the physicalDevice
        physicalDevice.setDeviceModelIdentifier(UPDATED_DEVICE_MODEL_IDENTIFIER);
        physicalDevice.setDeviceModelName(UPDATED_DEVICE_MODEL_NAME);
        physicalDevice.setSerialNumber(UPDATED_SERIAL_NUMBER);
        physicalDevice.setPaymentCaptureDeviceIdentifier(UPDATED_PAYMENT_CAPTURE_DEVICE_IDENTIFIER);
        physicalDevice.setGateWayIdentifier(UPDATED_GATE_WAY_IDENTIFIER);
        physicalDevice.setPartyLevel(UPDATED_PARTY_LEVEL);
        physicalDevice.setAppliedToPartyIdentifier(UPDATED_APPLIED_TO_PARTY_IDENTIFIER);
        physicalDevice.setPartyRoleIdentifier(UPDATED_PARTY_ROLE_IDENTIFIER);
        physicalDevice.setPartyRoleName(UPDATED_PARTY_ROLE_NAME);
        physicalDevice.setDeviceStatusIdentifier(UPDATED_DEVICE_STATUS_IDENTIFIER);
        physicalDevice.setDeviceFirmwareVersion(UPDATED_DEVICE_FIRMWARE_VERSION);
        physicalDevice.setDeviceManufacturerIdentifier(UPDATED_DEVICE_MANUFACTURER_IDENTIFIER);
        physicalDevice.setDeviceManufacturerName(UPDATED_DEVICE_MANUFACTURER_NAME);
        physicalDevice.setP2PEComplianceFlag(UPDATED_P2_PECOMPLIANCE_FLAG);
        physicalDevice.setDeviceOwnershipFlag(UPDATED_DEVICE_OWNERSHIP_FLAG);
        

        restPhysicalDeviceMockMvc.perform(put("/api/physicalDevices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(physicalDevice)))
                .andExpect(status().isOk());

        // Validate the PhysicalDevice in the database
        List<PhysicalDevice> physicalDevices = physicalDeviceRepository.findAll();
        assertThat(physicalDevices).hasSize(databaseSizeBeforeUpdate);
        PhysicalDevice testPhysicalDevice = physicalDevices.get(physicalDevices.size() - 1);
        assertThat(testPhysicalDevice.getDeviceModelIdentifier()).isEqualTo(UPDATED_DEVICE_MODEL_IDENTIFIER);
        assertThat(testPhysicalDevice.getDeviceModelName()).isEqualTo(UPDATED_DEVICE_MODEL_NAME);
        assertThat(testPhysicalDevice.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testPhysicalDevice.getPaymentCaptureDeviceIdentifier()).isEqualTo(UPDATED_PAYMENT_CAPTURE_DEVICE_IDENTIFIER);
        assertThat(testPhysicalDevice.getGateWayIdentifier()).isEqualTo(UPDATED_GATE_WAY_IDENTIFIER);
        assertThat(testPhysicalDevice.getPartyLevel()).isEqualTo(UPDATED_PARTY_LEVEL);
        assertThat(testPhysicalDevice.getAppliedToPartyIdentifier()).isEqualTo(UPDATED_APPLIED_TO_PARTY_IDENTIFIER);
        assertThat(testPhysicalDevice.getPartyRoleIdentifier()).isEqualTo(UPDATED_PARTY_ROLE_IDENTIFIER);
        assertThat(testPhysicalDevice.getPartyRoleName()).isEqualTo(UPDATED_PARTY_ROLE_NAME);
        assertThat(testPhysicalDevice.getDeviceStatusIdentifier()).isEqualTo(UPDATED_DEVICE_STATUS_IDENTIFIER);
        assertThat(testPhysicalDevice.getDeviceFirmwareVersion()).isEqualTo(UPDATED_DEVICE_FIRMWARE_VERSION);
        assertThat(testPhysicalDevice.getDeviceManufacturerIdentifier()).isEqualTo(UPDATED_DEVICE_MANUFACTURER_IDENTIFIER);
        assertThat(testPhysicalDevice.getDeviceManufacturerName()).isEqualTo(UPDATED_DEVICE_MANUFACTURER_NAME);
        assertThat(testPhysicalDevice.getP2PEComplianceFlag()).isEqualTo(UPDATED_P2_PECOMPLIANCE_FLAG);
        assertThat(testPhysicalDevice.getDeviceOwnershipFlag()).isEqualTo(UPDATED_DEVICE_OWNERSHIP_FLAG);
    }

    @Test
    @Transactional
    public void deletePhysicalDevice() throws Exception {
        // Initialize the database
        physicalDeviceRepository.saveAndFlush(physicalDevice);

		int databaseSizeBeforeDelete = physicalDeviceRepository.findAll().size();

        // Get the physicalDevice
        restPhysicalDeviceMockMvc.perform(delete("/api/physicalDevices/{id}", physicalDevice.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PhysicalDevice> physicalDevices = physicalDeviceRepository.findAll();
        assertThat(physicalDevices).hasSize(databaseSizeBeforeDelete - 1);
    }
}
