import java.util.*;
import java.io.*;

// write your matric number here: A0116631N
// write your name here: Eugene
// write list of collaborators here (reading someone's post in Facebook group and using the idea is counted as collaborating):

enum Gender {
	MALE,
	FEMALE;
}

/**
 * Baby Name Data Structure
 */
class BabyName implements Comparable<String>{

	private String _name;
	private Gender _gender;
	
	public BabyName(String name, Gender gender){
		this._name = name;
		this._gender = gender;
	}
	
	public String getName(){
		return this._name;
	}
	
	public Gender getGender(){
		return this._gender;			
	}

	@Override
  public int compareTo(String name) {
		return this.getName().compareTo(name);
  }
}

class BabyNames {

  // Declaration
  // --------------------------------------------
	private TreeSet<BabyName> _female_baby_names;
	private TreeSet<BabyName> _male_baby_names;
  // --------------------------------------------

  public BabyNames() {
  	Comparator<BabyName> babyname_comparator = new Comparator<BabyName>() {
  		
			@Override
      public int compare(BabyName o1, BabyName o2) {
				return o1.compareTo(o2.getName());		  
      }
			
		};
  	_female_baby_names = new TreeSet<BabyName>(babyname_comparator);
  	_male_baby_names = new TreeSet<BabyName>(babyname_comparator);
  }
  
  private Gender getGender(int genderId){
  	Gender _gender;
  	if (genderId == 1){		
  		_gender = Gender.MALE;
  	} else if (genderId == 2){
  		_gender = Gender.FEMALE;
  	} else {
  		_gender = null; 
  	}
  	return _gender;
  }

  void AddSuggestion(String babyName, int genderSuitability) {
    // Insert the information (babyName, genderSuitability)
    // into your chosen data structure
    // --------------------------------------------

  	Gender _gender = getGender(genderSuitability);
  	BabyName bn = new BabyName(babyName, _gender);
  	
  	if (_gender == Gender.MALE){
  		this._male_baby_names.add(bn);
  	} else if (_gender == Gender.FEMALE){
  		this._female_baby_names.add(bn);
  	}
    // --------------------------------------------
  }

  int Query(String START, String END, int genderPreference) {
    int ans = 0;

    // how many baby names starts
    // with prefix that is inside query interval [START..END)
    // --------------------------------------------
    // System.out.println(START + ", " + END);
    Gender gender_pref = getGender(genderPreference);
    if (gender_pref == Gender.MALE || gender_pref == null) {
    	for (BabyName baby_name: this._male_baby_names){		
  			int lexical_start_bound = baby_name.compareTo(START);
  			int lexical_end_bound = baby_name.compareTo(END);
  			if (lexical_start_bound >= 0 && lexical_end_bound < 0){
  				ans += 1;
  			}
//  			System.out.println(baby_name.getName() + ":" + lexical_start_bound + ".." +lexical_end_bound);
    	}
    }
    
    if (gender_pref == Gender.FEMALE || gender_pref == null){
    	for (BabyName baby_name: this._female_baby_names){		
  			int lexical_start_bound = baby_name.compareTo(START);
  			int lexical_end_bound = baby_name.compareTo(END);
  			if (lexical_start_bound >= 0 && lexical_end_bound < 0){
  				ans += 1;
  			}
//  			System.out.println(baby_name.getName() + ":" + lexical_start_bound + ".." +lexical_end_bound);
    	}
    }
    // --------------------------------------------

    return ans;
  }

  void run() throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    while (true) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      if (command == 0) // end of input
        break;
      else if (command == 1) // Add Suggestion
        AddSuggestion(st.nextToken(), Integer.parseInt(st.nextToken()));
      else // if (command == 2) // Query
        pr.println(Query(st.nextToken(),      // START
                         st.nextToken(),      // END
                         Integer.parseInt(st.nextToken()))); // GENDER
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    BabyNames ps1 = new BabyNames();
    ps1.run();
  }
}
