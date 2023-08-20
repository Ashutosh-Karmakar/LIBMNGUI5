package customer.librarymng_backend.handlers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cds.ql.Select;
import com.sap.cds.ql.Update;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.cds.CdsCreateEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.ql.cqn.CqnUpdate;
//
import cds.gen.booksservice.BooksStock_;
import cds.gen.booksservice.BooksStock;
import cds.gen.booksservice.BooksService_;
import cds.gen.booksservice.Books_;
import cds.gen.booksservice.Books;

@Component
@ServiceName(BooksService_.CDS_NAME)
public class BooksStockHandler implements EventHandler{
    @Autowired
    PersistenceService db;

    @Before(event = CqnService.EVENT_CREATE, entity = BooksStock_.CDS_NAME)
    public void beforeCreate(List<BooksStock> booksStocks){
        for(BooksStock booksStock: booksStocks) {
            String bookId = booksStock.getBook();
            CqnSelect select = Select.from(Books_.class).where(b -> b.ID().eq(bookId));
            Books book = db.run(select).first(Books.class)
             .orElseThrow(() -> new ServiceException(ErrorStatuses.NOT_FOUND, "Book doesnot exist"));
            int stock = booksStock.getQuantity();
            book.setStock(book.getStock() + stock);
            System.out.println(book.getStock());
            CqnUpdate update = Update.entity(Books_.class).data(book).where(b -> b.ID().eq(bookId));
            db.run(update);
        }
    }
}
