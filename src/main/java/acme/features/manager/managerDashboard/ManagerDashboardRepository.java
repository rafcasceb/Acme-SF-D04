
package acme.features.manager.managerDashboard;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.UserStoryPriority;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select us.priority from UserStory us where us.manager.id = :managerId")
	Collection<UserStoryPriority> findUserStoriesAsPriorities(int managerId);

	default Map<UserStoryPriority, Integer> totalNumberUserStoriesByPriority(final int managerId) {
		Map<UserStoryPriority, Integer> sol;
		Collection<UserStoryPriority> prioritiesQuery;

		sol = new EnumMap<>(UserStoryPriority.class);
		prioritiesQuery = this.findUserStoriesAsPriorities(managerId);

		for (UserStoryPriority priority : UserStoryPriority.values())
			sol.put(priority, 0);

		for (UserStoryPriority priority : prioritiesQuery)
			sol.put(priority, sol.get(priority) + 1);

		return sol;
	}

	@Query("select avg(us.estimatedCostInHours) from UserStory us where us.manager.id = :managerId")
	Double averageEstimatedCostUserStories(int managerId);

	@Query("select stddev(us.estimatedCostInHours) from UserStory us where us.manager.id = :managerId")
	Double deviationEstimatedCostUserStories(int managerId);

	@Query("select min(us.estimatedCostInHours) from UserStory us where us.manager.id = :managerId")
	Integer minimumEstimatedCostUserStories(int managerId);

	@Query("select max(us.estimatedCostInHours) from UserStory us where us.manager.id = :managerId")
	Integer maximumEstimatedCostUserStories(int managerId);

	@Query("select avg(p.estimatedCostInHours) from Project p where p.manager.id = :managerId")
	Double averageCostProjects(int managerId);

	@Query("select stddev(p.estimatedCostInHours) from Project p where p.manager.id = :managerId")
	Double deviationCostProjects(int managerId);

	@Query("select min(p.estimatedCostInHours) from Project p where p.manager.id = :managerId")
	Integer minimumCostProjects(int managerId);

	@Query("select max(p.estimatedCostInHours) from Project p where p.manager.id = :managerId")
	Integer maximumCostProjects(int managerId);

	@Query("select count(p) from Project p where p.manager.id = :managerId")
	int numberProjects(int managerId);

}
