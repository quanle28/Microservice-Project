package com.aql.borrowingservice.query.projection;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aql.borrowingservice.command.api.data.BorrowRepository;
import com.aql.borrowingservice.command.api.data.Borrowing;
import com.aql.borrowingservice.query.model.BorrowingResponseModel;
import com.aql.borrowingservice.query.queries.GetAllBorrowing;
import com.aql.borrowingservice.query.queries.GetListBorrowingByEmployeeQuery;
import com.aql.commonservice.model.BookResponseCommonModel;
import com.aql.commonservice.model.EmployeeResponseCommonModel;
import com.aql.commonservice.query.GetDetailsBookQuery;
import com.aql.commonservice.query.GetDetailsEmployeeQuery;

@Component
public class BorrowingProjection {
	@Autowired
	private BorrowRepository borrowRepository;

	@Autowired
	private QueryGateway queryGateway;

	@QueryHandler
	public List<BorrowingResponseModel> handle(GetAllBorrowing query) {
		List<BorrowingResponseModel> list = new ArrayList<>();
		List<Borrowing> listEntity = borrowRepository.findAll();
		listEntity.forEach(s -> {
			BorrowingResponseModel model = new BorrowingResponseModel();
			BeanUtils.copyProperties(s, model);
			model.setNameBook(queryGateway.query(new GetDetailsBookQuery(model.getBookId()),
					ResponseTypes.instanceOf(BookResponseCommonModel.class)).join().getName());
			EmployeeResponseCommonModel employee = queryGateway
					.query(new GetDetailsEmployeeQuery(model.getEmployeeId()),
							ResponseTypes.instanceOf(EmployeeResponseCommonModel.class))
					.join();
			model.setNameEmployee(employee.getFirstName() + " " + employee.getLastName());
			list.add(model);
		});
		return list;
	}

	@QueryHandler
	public List<BorrowingResponseModel> handle(GetListBorrowingByEmployeeQuery query) {
		List<BorrowingResponseModel> list = new ArrayList<>();
		List<Borrowing> listEntity = borrowRepository.findByEmployeeIdAndReturnDateIsNull(query.getEmployeeId());
		listEntity.forEach(s -> {
			BorrowingResponseModel model = new BorrowingResponseModel();
			BeanUtils.copyProperties(s, model);
			model.setNameBook(queryGateway.query(new GetDetailsBookQuery(model.getBookId()),
					ResponseTypes.instanceOf(BookResponseCommonModel.class)).join().getName());
			EmployeeResponseCommonModel employee = queryGateway
					.query(new GetDetailsEmployeeQuery(model.getEmployeeId()),
							ResponseTypes.instanceOf(EmployeeResponseCommonModel.class))
					.join();
			model.setNameEmployee(employee.getFirstName() + " " + employee.getLastName());
			list.add(model);
		});
		return list;
	}
}
