package com.forsrc.ejb.user.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.forsrc.ejb.user.dao.UserDao;
import com.forsrc.ejb.user.dao.UserDaoLocal;
import com.forsrc.ejb.user.dao.impl.UserDaoImpl;
import com.forsrc.ejb.user.service.UserService;
import com.forsrc.ejb.user.service.UserServiceLocal;
import com.forsrc.ejb.user.service.UserServiceRemote;
import com.forsrc.exception.DaoException;
import com.forsrc.exception.ServiceException;
import com.forsrc.pojo.User;

@Stateless
@Local(UserServiceLocal.class)
@Remote(UserServiceRemote.class)
public class UserServiceImpl implements UserService, UserServiceRemote,
		UserServiceLocal {

	@EJB(mappedName = "java:app/MyEjb/UserDaoImpl!com.forsrc.ejb.user.dao.UserDaoLocal")
	private UserDaoLocal userDao;

	@Override
	public User get(long id) throws ServiceException {

		return userDao.find(id);
	}

	@Override
	public void save(User user) throws ServiceException {
		userDao.save(user);
	}

	@Override
	public List<User> list(int start, int size) throws DaoException {
		return userDao.list("from " + User.class.getName(), null, start, size);
	}

}
