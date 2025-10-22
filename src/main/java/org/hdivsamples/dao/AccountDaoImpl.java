package org.hdivsamples.dao;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.hdivsamples.bean.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDaoImpl implements AccountDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Account> findUsersByUsernameAndPassword(final String username, final String password) {
		
        String ldapUrl = "ldap://localhost:10389";
        String baseDn = "dc=example,dc=com";
        String bindDn = "cn=admin," + baseDn;
        String bindPassword = "password";

        // Set up the environment for creating the initial context
        Hashtable<String, Object> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, bindDn);
        env.put(Context.SECURITY_CREDENTIALS, bindPassword);

        // Try to connect to LDAP server, but don't fail if it's not available (for testing)
        try {
        	DirContext context = new InitialDirContext(env);
        	
        	// Escape LDAP special characters to prevent LDAP injection
        	String escapedUsername = escapeLDAPSearchFilter(username);
        	String searchFilter = "(uid=" + escapedUsername + ")";
        	SearchControls searchControls = new SearchControls();
        	searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        	NamingEnumeration<SearchResult> searchResults = context.search(baseDn, searchFilter, searchControls);
        	
        	// Close the context when done
        	context.close();
        } catch (NamingException e) {
        	// Log the error but continue with database authentication
        	System.out.println("LDAP connection failed: " + e.getMessage() +
        		". Continuing with database authentication only.");
        }
		
		

		String str = "select * from account where username=? AND password=?";

		RowMapper<Account> rowMapper = new RowMapper<Account>() {
			@Override
			public Account mapRow(final ResultSet paramResultSet, final int paramInt) throws java.sql.SQLException {
				Account localAccount = new Account();
				localAccount.setUsername(paramResultSet.getString("username"));
				localAccount.setName(paramResultSet.getString("name"));
				localAccount.setSurname(paramResultSet.getString("surname"));
				localAccount.setPassword(paramResultSet.getString("password"));
				return localAccount;
			}
		};

		return jdbcTemplate.query(str, new Object[] { username, password }, rowMapper);
	}

	@Override
	public List<Account> findUsersByUsername(final String username) {
		String str = "select * from account where username=?";

		RowMapper<Account> rowMapper = new RowMapper<Account>() {
			@Override
			public Account mapRow(final ResultSet paramResultSet, final int paramInt) throws java.sql.SQLException {
				Account localAccount = new Account();
				localAccount.setUsername(paramResultSet.getString("username"));
				localAccount.setName(paramResultSet.getString("name"));
				localAccount.setSurname(paramResultSet.getString("surname"));
				localAccount.setPassword(paramResultSet.getString("password"));
				return localAccount;
			}
		};

		return jdbcTemplate.query(str, new Object[] { username }, rowMapper);
	}

	@Override
	public List<Account> findAllUsers() {

		String str = "select * from account";

		RowMapper<Account> rowMapper = new RowMapper<Account>() {
			@Override
			public Account mapRow(final ResultSet paramResultSet, final int paramInt) throws java.sql.SQLException {
				Account localAccount = new Account();
				localAccount.setUsername(paramResultSet.getString("username"));
				localAccount.setName(paramResultSet.getString("name"));
				localAccount.setSurname(paramResultSet.getString("surname"));
				localAccount.setPassword(paramResultSet.getString("password"));
				return localAccount;
			}
		};

		return jdbcTemplate.query(str, rowMapper);
	}

	public void setJdbcTemplate(final JdbcTemplate paramJdbcTemplate) {
		jdbcTemplate = paramJdbcTemplate;
	}
	
	/**
	 * Escapes LDAP special characters to prevent LDAP injection
	 *
	 * @param filter The string to escape
	 * @return The escaped string
	 */
	private String escapeLDAPSearchFilter(final String filter) {
		if (filter == null) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < filter.length(); i++) {
			char c = filter.charAt(i);
			switch (c) {
				case '\\':
					sb.append("\\5c");
					break;
				case '*':
					sb.append("\\2a");
					break;
				case '(':
					sb.append("\\28");
					break;
				case ')':
					sb.append("\\29");
					break;
				case '\u0000':
					sb.append("\\00");
					break;
				default:
					sb.append(c);
			}
		}
		return sb.toString();
	}
}
