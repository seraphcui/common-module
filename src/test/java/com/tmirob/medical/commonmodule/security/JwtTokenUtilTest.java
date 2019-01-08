/*
 * Copyright (c) 2017. All rights reserved by Taimi Robot.
 * Created by yaocui on 17-8-21 下午4:08.
 */

package com.tmirob.medical.commonmodule.security;

import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtTokenUtilTest {

    private static final String TEST_USERNAME = "testUser";

    @Mock
    private TimeProvider timeProvider;

    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(jwtTokenUtil, "expiration", 3600L); // one hour
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", "mySecret");
    }

    @Test
    public void testGenerateTokenGeneratesDifferentTokensForDifferentCreationDates() throws Exception {
        when(timeProvider.now()).thenReturn(DateUtil.yesterday()).thenReturn(DateUtil.now());
        final String token = createToken();
        final String laterToken = createToken();
        assertThat(token).isNotEqualTo(laterToken);
    }

    @Test
    public void getUsernameFromToken() throws Exception {
        when(timeProvider.now()).thenReturn(DateUtil.now());
        final String token = createToken();
        assertThat(jwtTokenUtil.getUsernameFromToken(token)).isEqualTo(TEST_USERNAME);
    }

    @Test
    public void getCreatedDateFromToken() throws Exception {
        final Date now = DateUtil.now();
        when(timeProvider.now()).thenReturn(now);
        final String token = createToken();
        assertThat(jwtTokenUtil.getCreatedDateFromToken(token)).isInSameMinuteWindowAs(now);
    }

    @Test
    public void getExpirationDateFromToken() throws Exception {
        final Date now = DateUtil.now();
        when(timeProvider.now()).thenReturn(now);
        final String token = createToken();

        final Date expirationDateFromToken = jwtTokenUtil.getExpirationDateFromToken(token);
        assertThat(DateUtil.timeDifference(expirationDateFromToken, now)).isCloseTo(3600000L, within(1000L));
    }

    @Test
    public void canValidateToken() throws Exception {
        when(timeProvider.now()).thenReturn(DateUtil.now());
        UserDetails person = mock(MyUserDetails.class);
        when(person.getUsername()).thenReturn(TEST_USERNAME);

        String token = createToken();
        assertThat(jwtTokenUtil.validateToken(token, person)).isTrue();
    }

    private String createToken() {
        MyUserDetails person = new MyUserDetails(1L, TEST_USERNAME, TEST_USERNAME, null, null);
        return jwtTokenUtil.generateToken(person);
    }
}
