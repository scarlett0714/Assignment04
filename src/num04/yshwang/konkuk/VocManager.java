package num04.yshwang.konkuk;

import java.io.File;

import java.io.FileNotFoundException;
import java.util.*;


public class VocManager {
	private String userName;
	ArrayList<Word> voc = new ArrayList<>();
	int[] voc2;
	
	static Scanner scan = new Scanner(System.in);

	public VocManager(String userName) {
		this.userName = userName;
	}
	
	
	void addWord(Word word) {
		voc.add(word);
	}
	
	void makeVoc(String fileName) {
		
		try(Scanner scan = new Scanner(new File(fileName))){
			while(scan.hasNextLine()) {
				String str = scan.nextLine();
				String[] temp = str.split("\t");
				addWord(new Word(temp[0].trim(), temp[1].trim()));
			}
			System.out.println(userName+"의 단어장이 생성되었습니다.");
			voc2 = new int[voc.size()];
			menu();
			
		}catch(FileNotFoundException e) {
			System.out.println("파일이름을 확인해 주세요.");
		}
	}

	void menu() {
		int choice = 0;
		while (choice != 4) {
			System.out.println("\n------" + userName + "의 단어장 ------");
			System.out.println("1) 단어검색 2) 객관식 퀴즈 3) 빈출단어 4) 종료");
			System.out.print("메뉴를 선택하세요 : ");
			try {
				choice = scan.nextInt();
				scan.nextLine();
				System.out.println();
				
				
				if (choice >= 1 && choice <= 4) {
					switch (choice) {
					case 1:
						searchVoc();
						break;
					case 2:
						quiz();
						break;
					case 3:
						countWord();
						break;
					case 4:
						System.out.println(userName + "의 단어장 프로그램을 종료합니다.");
						break;

					}
				}
			} catch (InputMismatchException e) {
				System.out.println("숫자를 입력해 주세요.");
				scan.nextLine();
			}

		}

	}
	

	public void countWord() {
		int maxindex=0;
		int max = 0;
		int count = 0;
		while (count < 5) {
			for (int i = 0; i < voc2.length; i++) {
				if (voc2[maxindex] <= voc2[i]) {
					maxindex = i;
				}
			}
			max = voc2[maxindex];
			System.out.println(max + "번->" + voc.get(maxindex).eng + " : " + voc.get(maxindex).kor);
			count++;
			voc2[maxindex]=0;
		}
		

	}


	public void quiz() {
		
		long time1 = System.currentTimeMillis();
		
		int count =0; //퀴즈 횟수
		int score =0; //정답인 개수
		Random rand = new Random();
		
		for(int i=0;i<voc2.length;i++) {
			voc2[i] = 0;
		}
		
		
		
		
		while(count<10) {
			
			int[] quiz = chooseQuizNum();
			int answerindex = rand.nextInt(4);
			
			for(int i=0;i<quiz.length;i++) {
				for(int j=0;j<voc2.length;j++) {
					if(quiz[i]==j)
						voc2[j] = voc2[j] + 1;
				}
				
			}
			
			try {
			System.out.println("------ 객관식 퀴즈 "+(count+1)+"번 ------");
			System.out.println(voc.get(quiz[answerindex]).eng+"의 뜻은 무엇일까요?");
			for(int i=0;i<quiz.length;i++) {
				System.out.println((i+1)+") "+voc.get(quiz[i]).kor);
			}
			System.out.print("답을 입력하세요 : ");
			int answer = scan.nextInt();
			if((answer - 1)==answerindex) {
				System.out.println("정답입니다.");
				score++;
			} else if(answer>=1 && answer<=4) {
				System.out.println("틀렸습니다. 정답은 "+ (answerindex+1)+"번 입니다.");
			} else {
				throw new Exception("예외 발생!! 1~4번 중 하나를 선택하세요.");
			}
			}catch(InputMismatchException e){
				System.err.println("1~4번 중 하나를 선택하세요!!!");
				scan.nextLine();
			}catch(Exception e) {
				System.err.println(e.getMessage());
			}
			
			count++;
		}
		
		long time2 = System.currentTimeMillis();
		System.out.println(userName+"님 10문제 중 "+score+"개 맞추셨고, 총 "+(time2-time1)/1000+"초 소요되었습니다.");
		

	}



	public int[] chooseQuizNum() {
		int[] quiz = new int[4];
		Random rand = new Random();
		for(int i=0;i<quiz.length;i++) {
			quiz[i] = rand.nextInt(voc.size());
			for(int j=0;j<i;j++) {
				if(quiz[j]==quiz[i] || voc.get(quiz[j]).kor.equals(voc.get(quiz[i]).kor)) {
					i--;
					break;
				}
			}
		}
		return quiz;
	}



	public void searchVoc() {
		System.out.println("------단어 검색------");
		System.out.print("검색할 단어를 입력하세요 (영단어) : ");
		String sWord = scan.nextLine();
		sWord = sWord.trim();
		boolean findResult = false;
		for (Word word : voc) {
			if (word != null && word.eng.equals(sWord)) { 
				System.out.println("단어의 뜻 : "+word.kor);
				findResult = true;
				break;
			}
		}
		if (!findResult) {
			System.out.println("단어장에 등록된 단어가 아닙니다.");
		}

	}

}
