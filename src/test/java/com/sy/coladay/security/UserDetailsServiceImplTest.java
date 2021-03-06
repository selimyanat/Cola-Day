package com.sy.coladay.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.sy.coladay.user.User;
import com.sy.coladay.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Test class for {@link UserDetailsServiceImpl}.
 *
 * @author selim
 */
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserDetailsServiceImpl underTest;

  @Test
  void loadUserByUsername_userFound_returnsUserPrincipal_ok() {
    when(userRepository.findByName(anyString())).thenReturn(Optional.of(mock(User.class)));

    final UserDetails userDetails = underTest.loadUserByUsername("user");
    assertThat(userDetails)
        .isNotNull()
        .isInstanceOf(UserPrincipal.class);
  }

  @Test
  void loadUserByUsername_userNotFound_throwException() {
    when(userRepository.findByName(anyString())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> underTest.loadUserByUsername("user"))
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessage("Username user not found");
  }
}