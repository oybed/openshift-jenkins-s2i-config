import jenkins.model.*
import hudson.security.*
import org.jenkinsci.plugins.*
import hudson.security.FullControlOnceLoggedInAuthorizationStrategy

def env = System.getenv();

String server = env['LDAP_SERVER'];
String rootDN = env['LDAP_ROOT_DN'];
String userSearchBase = env['LDAP_USER_SEARCH_BASE'];
String userSearch = env['LDAP_USER_SEARCH'];
String groupSearchBase = env['LDAP_GROUP_SEARCH_BASE'];
String managerDN = env['LDAP_MANAGER_DN'];
String managerAccess = env['LDAP_ACCESS'];
boolean inhibitInferRootDN = env['INHIBIT_INFER_ROOT_DN'];

if(server == null) {
  java.util.logging.Logger.getLogger("LABS").info("ldap not set")
} else {
  java.util.logging.Logger.getLogger("LABS").info("Setting ldap security realm")

  SecurityRealm ldapRealm = new LDAPSecurityRealm(server, rootDN, userSearchBase, userSearch, groupSearchBase, managerDN, managerAccess, inhibitInferRootDN)
  Jenkins.instance.setSecurityRealm(ldapRealm)
  Jenkins.instance.setAuthorizationStrategy(new FullControlOnceLoggedInAuthorizationStrategy())
  Jenkins.instance.save()
}