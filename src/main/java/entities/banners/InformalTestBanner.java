
package entities.banners;

import java.time.Instant;
import java.util.Date;

public class InformalTestBanner {

	public static void main(final String[] args) {
		try {
			Date moment = Date.from(Instant.now());
			Banner banner1 = new Banner(moment, Date.from(Instant.now().plusSeconds(3600)), Date.from(Instant.now().plusSeconds(3600 * 24 * 8)), "https://example.com/picture1.jpg", "Slogan para banner 1", "https://example.com/target1");
			System.out.println("Banner 1 creado correctamente");

			Banner banner2 = new Banner(moment, Date.from(Instant.now().minusSeconds(3600)), Date.from(Instant.now().plusSeconds(3600 * 24 * 6)), "https://example.com/picture2.jpg", "Slogan para banner 2", "https://example.com/target2");
			System.out.println("Banner 2 creado correctamente");

		} catch (IllegalArgumentException e) {
			System.out.println("Error al crear el banner: " + e.getMessage());
		}
		try {
			Date moment = Date.from(Instant.now());
			Banner banner3 = new Banner(moment, Date.from(Instant.now().minusSeconds(3600)), Date.from(Instant.now().plusSeconds(3600 * 24 * 8)), "https://example.com/picture1.jpg", "Slogan para banner 1", "https://example.com/target1");
			System.out.println("Banner 3 creado correctamente");

		} catch (IllegalArgumentException e) {
			System.out.println("Error al crear el banner: " + e.getMessage());
		}
	}

}
