
package acme.features.sponsor.sponsorDashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(i) FROM Invoice i WHERE i.sponsorship.sponsor.id = :id AND i.tax <= 0.21")
	int countInvoicesWithTaxLessThanOrEqual21(int id);

	@Query("SELECT COUNT(s) FROM Sponsorship s WHERE s.sponsor.id = :id AND s.link IS NOT NULL")
	int countSponsorshipsWithLink(int id);

	@Query("SELECT AVG(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id")
	Double averageAmountSponsorships(int id);

	@Query("SELECT STDDEV(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id")
	Double stdevAmountSponsorships(int id);

	@Query("SELECT MIN(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id")
	Double minimumAmountSponsorships(int id);

	@Query("SELECT MAX(s.amount.amount) FROM Sponsorship s WHERE s.sponsor.id = :id")
	Double maximumAmountSponsorships(int id);

	@Query("SELECT AVG(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id = :id")
	Double averageQuantityInvoices(int id);

	@Query("SELECT STDDEV(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id = :id")
	Double stdevQuantityInvoices(int id);

	@Query("SELECT MIN(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id = :id")
	Double minimumQuantityInvoices(int id);

	@Query("SELECT MAX(i.quantity.amount) FROM Invoice i WHERE i.sponsorship.sponsor.id = :id")
	Double maximumQuantityInvoices(int id);

}
