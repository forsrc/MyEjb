package com.forsrc.ejb.user.service;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.forsrc.exception.DaoException;
import com.forsrc.exception.ServiceException;
import com.forsrc.pojo.User;

@TransactionManagement(TransactionManagementType.CONTAINER)
public interface UserService {

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public User get(long id) throws ServiceException;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<User> list(int start, int size) throws DaoException;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void save(User user) throws ServiceException;
}
