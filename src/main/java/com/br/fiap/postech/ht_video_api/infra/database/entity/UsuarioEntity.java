package com.br.fiap.postech.ht_video_api.infra.database.entity;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Data
@Document(collection = "usuario")
public class UsuarioEntity {//implements UserDetails {

	private static final long serialVersionUID = 369307964226625936L;

	private @MongoId ObjectId id;
	
	private String nomeCompleto;
	private String username;
	private String password;
	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return List.of();
//	}
//	
//	@Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
	
}
