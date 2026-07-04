package bg.softuni.booknest.repository;

import bg.softuni.booknest.model.entity.Book;
import bg.softuni.booknest.model.enums.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findTop8ByStatusOrderByCreatedOnDesc(BookStatus status);
    List<Book> findTop4ByFeaturedTrueAndStatusOrderByCreatedOnDesc(BookStatus status);
    List<Book> findAllByStatusOrderByRentalPriceDesc(BookStatus status);
    List<Book> findAllByStatus(BookStatus status);
    @Query("""
    SELECT b
    FROM Book b
    WHERE b.status = :status
      AND (
            LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%'))
         OR LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%'))
         OR LOWER(b.description) LIKE LOWER(CONCAT('%', :query, '%'))
      )
    ORDER BY b.title ASC
    """)
    List<Book> searchByTitleAuthorOrGenre(@Param("status") BookStatus status,
                                          @Param("query") String query);
}
