package com.mytoy.bookstore.mapper;

import com.mytoy.bookstore.dto.UserDto;
import com.mytoy.bookstore.dto.UserDto.UserDtoBuilder;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.model.User.UserBuilder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-15T20:02:29+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUserEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.id( userDto.getId() );
        user.uid( userDto.getUid() );
        user.password( userDto.getPassword() );
        user.name( userDto.getName() );
        user.nickname( userDto.getNickname() );
        if ( userDto.getBirth() != null ) {
            user.birth( LocalDate.parse( userDto.getBirth() ) );
        }
        user.phone( userDto.getPhone() );
        user.gender( userDto.getGender() );
        user.email( userDto.getEmail() );
        user.profileName( userDto.getProfileName() );
        user.profilePath( userDto.getProfilePath() );
        if ( userDto.getSince() != null ) {
            user.since( LocalDate.parse( userDto.getSince() ) );
        }

        return user.build();
    }

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.uid( user.getUid() );
        userDto.password( user.getPassword() );
        userDto.name( user.getName() );
        userDto.nickname( user.getNickname() );
        userDto.gender( user.getGender() );
        if ( user.getBirth() != null ) {
            userDto.birth( DateTimeFormatter.ISO_LOCAL_DATE.format( user.getBirth() ) );
        }
        userDto.phone( user.getPhone() );
        userDto.email( user.getEmail() );
        if ( user.getSince() != null ) {
            userDto.since( DateTimeFormatter.ISO_LOCAL_DATE.format( user.getSince() ) );
        }
        userDto.profileName( user.getProfileName() );
        userDto.profilePath( user.getProfilePath() );

        return userDto.build();
    }
}
