package codes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;
import com.sun.xml.internal.ws.Closeable;

import codes.datas.User;
import sun.awt.FwDispatcher;

public class MainDrive {

	public static void main(String[] args) {

//		Git과 연동해서 전화번호부 만들 예정

		printMenu();

	}

	public static void printMenu() {

//		메뉴 1, 2, 0번 -> 0번을 누르면 프로그램 종료
//		0번을 누를때 까지는 무한 반복.

		Scanner myScan = new Scanner(System.in);

		while (true) {

//			어떤 메뉴가 있는지 표기
			System.out.println("*******전화번호부*******");
			System.out.println("1) 전화번호 추가 등록");
			System.out.println("2) 전체 전화번호 목록 조회");
			System.out.println("0) 프로그램 종료");
			System.out.println("=====================");

//			실제 메뉴 입력받기					
			System.out.print("메뉴 선택 : ");
			int inputMenu = myScan.nextInt();

//			입력값 확인
			if (inputMenu == 0) {
//				프로그램 종료 해야함. => 무한반복 탈출.
				System.out.println("전화번호부를 종료합니다..");
				break;
			} else if (inputMenu == 1) {
//				전화번호 등록 기능 실행
				addPhoneNumToFile();
			} else if (inputMenu == 2) {
//				전화번호 전체 조회 기능 실행
				readAllPhoneNum();
			} else {
//				0,1,2 외의 값이 들어온 경우 대응.
				System.out.println("잘못된 입력입니다.");
				System.out.println("다시 입력해주세요.");
			}

		}

	}

//	파일에 저장된 전화번호 목록 출력
	public static void readAllPhoneNum() {

//		파일에 저장된 데이터 -> 자바 프로그램에서 활용. (File INPUT)
//		FileReader / BufferedReader 활용.

//		불러올 파일의 위치 지정 -> 저장할때 사용하는 파일명과 동일하게
		File file = new File("phoneBook.txt");

		try {
			
//			파일을 실제로 불러오는 클래스
			FileReader fr = new FileReader(file);
			
//			한줄씩 한꺼번에 불러오게 하는 클래스 -> fr은 한 글자씩. fr을 보조해서 한문장 로드.
			BufferedReader br = new BufferedReader(fr);
			
			
//			모든 연락처를 불러올때까지 반복
			
			while (true) {
				
//				한줄을 통째로 불러오기.
				String line = br.readLine();
				
//				불러온 내용 null인지 검사.
				if(line==null) {
//					더이상 불러올 내용이 없어서 null이 들어옴.
//					-> 다 읽었다. -> 무한반복 탈출.
					
					System.out.println("연락처를 모두 읽어왔습니다.");
					break;
				}
				
//				이 줄의 코드가 실행된다 : break를 안만났다 -> 불러온 내용이 null이 아니다.
//				-> 실제로 파일에 적혀있던 한줄이 line에 담겨있다
				
//				System.out.println(line);

//				사용자 정보를 가공해서 출력.
//				조경진(33세) : 010-5112-3237 양식으로 가공.
				
//				사용자 이름/폰번/나이 분리해서 변수로 저장하자.

//				String 클래스의 split 기능으로 정보항목들을 분리.(,기준으로)
				String[] userInfos = line.split(",");
				
//				이름/폰번/나이 저장
				String userName = userInfos[0];
				String userPhoneNum = userInfos[1];
//				나이 : 생년을 저장하고 계산.
//				String을 int로 변환해야 한다. -> Wrapper클래스 (Integer 활용)
				int userBirthYear = Integer.parseInt(userInfos[2]);
			
//				이름 /폰번/ 나이를 가지고 -> User 객체로 만들자.
				User user = new User(userName, userPhoneNum, userBirthYear);
				
//				만들어낸 user를 출력. -> user 클래스의 tostring 오버라이딩해서 제대로된 양식으로 가공하자.
				System.out.println(user);
			}
			
//			while 빠져나옴 : 파일을 다 읽었으니 빠져나왔다.
//			파일 사용을 종료. br부터 닫고, fr을 닫자.
			
			br.close();
			fr.close();
			
		} catch (FileNotFoundException e) {
			
//			읽어올 파일이 없는 경우. (삭제 or 아직 안만든경우)
			System.out.println("불러올 파일이 없습니다.");
			System.out.println("연락처를 저장하고 다시 시도해주세요.");
			
//			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("연락처를 읽어오는 중에 문제가 발생했습니다.");
			e.printStackTrace();
		}
		
	}

//	전화번호 + 이름 + 생년 정보 저장 기능.
	public static void addPhoneNumToFile() {

//		저장할 데이터를 입력 받자.
		Scanner myScan = new Scanner(System.in);

//		이름(String) -> 폰번(String) -> 생년(메모)(int) 순서대로 저장.

		System.out.print("이름 입력 : ");
		String name = myScan.nextLine(); // String 은 Line으로 입력받자

		System.out.print("전화번호 입력: ");
		String phoneNum = myScan.nextLine();

		System.out.print("생년 입력 : ");
		int birthYear = myScan.nextInt();

//		변수에 저장한 데이터를 묶어서 파일로 저장.
//		JAVA -> 보조기억장치로 내보내기 -> 파일 출력 (SAVE)

//		어느 파일을 이용할지 파일명 설정.
		File phoneBookFile = new File("phoneBook.txt");

//		파일 SAVE -> 파일 쓰기.
//		파일에 사용자정보는 (추가) 저장. -> 기존 내용에서 이어붙이기. (true의 역할)

		try {
			FileWriter fw = new FileWriter(phoneBookFile, true);

//			fw는 개발자가 다루기 불편함. = 보조도구 : bw
			BufferedWriter bw = new BufferedWriter(fw);

//			실제로 bw를 이용해서 연락처 정보 저장.

//			저장내용 : 3가지 정보를 한 줄로 묶어서.
//			ex.) 조경진, 010-5112-3237, 1988 -> 한줄짜리 String으로 저장.

//			                     !!!format 셋팅시 띄어쓰기가 있으면안됨!!!! 
			String infoStr = String.format("%s,%s,%d", name, phoneNum, birthYear);

//			묶인 한 줄을 파일에 기록			
			bw.append(infoStr);
//			파일에 기록하고 나면, 줄이 바뀌지 않는다 -> system.out.print 처럼 동작
//			한줄에 한명씩만 저장 예정. -> 줄을 바꿔주자.
			bw.newLine();

//			작업 완료 -> 열어둔 bw,fw 를 닫아주자.
			bw.close();
			fw.close();

			System.out.println("연락처 저장이 완료 되었습니다.");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
