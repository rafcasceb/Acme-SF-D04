
package acme.features.administrator.administratorDashboard;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("select count(c) from Client c")
	int totalNumberOfClients();

	@Query("select count(a) from Auditor a")
	int totalNumberOfAuditors();

	@Query("select count(d) from Developer d")
	int totalNumberOfDevelopers();

	@Query("select count(m) from Manager m")
	int totalNumberOfManagers();

	@Query("select count(c) from Sponsor c")
	int totalNumberOfSponsors();

	@Query("select count(n) from Notice n")
	int totalNumberNotices();

	@Query("select count(n) from Notice n where n.email is not null and n.link is not null")
	int totalNumberNoticesWithBothEmailAndLink();

	@Query("select count(o) from Objective o")
	int totalNumberObjectives();

	@Query("select count(o) from Objective o where o.critical = false")
	int totalNumberObjectivesNonCrit();

	@Query("select count(o) from Objective o where o.critical = true")
	int totalNumberObjectivesCrit();

	@Query("select avg(r.impact) from Risk r")
	Double avgImpactRisk();

	@Query("select avg(r.probability) from Risk r")
	Double avgProbRisk();

	@Query("select stddev(r.impact) from Risk r")
	Double stddevImpactRisk();

	@Query("select stddev(r.probability) from Risk r")
	Double stddevProbRisk();

	@Query("select min(r.impact) from Risk r")
	Double minImpactRisk();

	@Query("select min(r.probability) from Risk r")
	Double minProbRisk();

	@Query("select max(r.impact) from Risk r")
	Double maxImpactRisk();

	@Query("select max(r.probability) from Risk r")
	Double maxProbRisk();

	//@Query("select avg(sub.count) from (select count(c) as count from Claim c where c.moment >= :threshold group by c) as sub")
	//Double avgNumberOfClaimPostedTenWeeksAgo(Date threshold);

	//@Query("select sqrt(avg((sub.count - avg.count) * (sub.count - avg.count))) " + "from (select count(c) as count from Claim c where c.moment >= :threshold group by c) as sub, "
	//	+ "(select avg(cnt) as count from (select count(c) as cnt from Claim c where c.moment >= :threshold group by c) as counts) as avg")
	//Double stddevNumberOfClaimPostedTenWeeksAgo(Date threshold);

	@Query("select count(c) from Claim c where c.moment >= :threshold")
	Double maxNumberOfClaimPostedTenWeeksAgo(Date threshold);

	@Query("select count(c) from Claim c where c.moment >= :threshold")
	int NumberOfClaimPostedTenWeeksAgo(Date threshold);

	@Query("select count(c) from Claim c")
	int NumberOfClaimPosted();

}
