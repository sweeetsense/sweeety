package pack.mybatis;

import java.util.List;

import pack.business.DataDTO;
import pack.business.ProcessDAO;

public class Main {

	public static void main(String[] args) {
		ProcessDAO dao = ProcessDAO.getInstance();
		
		try {
			
			System.out.println("\n자료 insert --- ");
			/*
			DataDTO dataDto = new DataDTO();
			dataDto.setCode("100");
			dataDto.setSang("초코우유");
			dataDto.setSu("5");
			dataDto.setDan("2000");
			dao.insertData(dataDto);
			*/
			
			System.out.println("\n자료 update --- ");
			/*
			DataDTO dataDto = new DataDTO();
			dataDto.setCode("100");
			dataDto.setSang("바나나우유");
			dao.updateData(dataDto);
			*/
			
			System.out.println("\n자료 delete --- ");
			boolean b = dao.deleteData(100);
			if(b){
				System.out.println("삭제 성공 :)");
			}else{
				System.out.println("삭제 실패 :(");
			}
			
			
			System.out.println("\n전체 자료 읽기 --- ");
			List<DataDTO> list = dao.selectDataAll();
			for(DataDTO s : list){
				System.out.println(s.getCode() + " " + s.getSang() + " " + s.getSu() + " " + s.getDan());
			}
			
			System.out.println("\n부분 자료 읽기 --- ");
			DataDTO s = dao.selectDataPart("1");			
			System.out.println(s.getCode() + " " + s.getSang() + " " + s.getSu() + " " + s.getDan());
		} catch (Exception e) {
			System.out.println("Err : " + e);
		}
	}

}
