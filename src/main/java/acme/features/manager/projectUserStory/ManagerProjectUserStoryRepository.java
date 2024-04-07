
package acme.features.manager.projectUserStory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.UserStory;

@Repository
public interface ManagerProjectUserStoryRepository extends AbstractRepository {

	@Query("select us from UserStory us where us.id = :id")
	UserStory findOneUserStoryById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select p from Project p left join ProjectUserStory p_us on p.id = p_us.project.id and p_us.userStory.id = :userStoryId where p.manager.id = :managerId and p_us.userStory.id is null and p.published = false")
	Collection<Project> findManyUnpublishedProjectsOfSameManagerUnlinkedToUserStory(int managerId, int userStoryId);

	@Query("select p_us.project from ProjectUserStory p_us where p_us.userStory.id = :userStoryId and p_us.project.published = false")
	Collection<Project> findManyUnpublishedProjectsLinkedToUserStory(int userStoryId);

	@Modifying
	@Query("delete from ProjectUserStory p_us where p_us.project.id = :projectId and p_us.userStory.id = :userStoryId")
	void deleteProjectUserStory(int projectId, int userStoryId);

}
