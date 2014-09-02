import java.util.*;
import java.io.*;

// Matric number: A0116631N
// Name: Eugene

// list of collaborators:
// - Introduction to Algorithms: 
//     Thomas H. Cormen, 
//     Charles E. Leiserson,
//     Ronald L. Rivest,
//     Clifford Stein.

enum Gender {
	MALE, FEMALE;
}

/**
 * Baby Name Data Structure
 */
class BabyName implements Comparable<BabyName> {

	private String _name;
	private Gender _gender;
	private BabyName _left;
	private BabyName _right;
	private BabyName _parent;
	private int _height;
	private int _size;

	public BabyName(String name, Gender gender) {
		this._name = name;
		this._gender = gender;
		this._size = 1;
	}

	public String getName() {
		return this._name;
	}

	public Gender getGender() {
		return this._gender;
	}

	@Override
	public int compareTo(BabyName bn) {
		return this.getName().compareTo(bn.getName());
	}

	public BabyName getRight() {
		return _right;
	}

	public void setRight(BabyName _right) {
		this._right = _right;
	}

	public BabyName getLeft() {
		return _left;
	}

	public void setLeft(BabyName _left) {
		this._left = _left;
	}

	public void setParent(BabyName _parent) {
		if (_parent != null) {
			if (this.compareTo(_parent) < 0) {
				_parent.setLeft(this);
			} else {
				_parent.setRight(this);
			}
		}
		this._parent = _parent;
	}

	public BabyName getParent() {
		return this._parent;
	}

	public int getHeight() {
		return _height;
	}

	public void setHeight(int height) {
		this._height = height;
	}
	
	public int getSize(){
		return this._size;
	}
	
	public void setSize(int size){
		this._size = size;
	}

	public boolean isHeightBalanced() {
		return Math.abs(this.balanceFactor()) <= 1;
	}

	public int balanceFactor() {
		int leftheight = 0;
		int rightheight = 0;
		BabyName left = this.getLeft();
		BabyName right = this.getRight();
		if (left != null) {
			leftheight = left.getHeight();
		}
		if (right != null) {
			rightheight = right.getHeight();
		}
		return leftheight - rightheight;
	}

	public void updateHeight() {
		int leftheight = 0;
		int rightheight = 0;
		BabyName left = this.getLeft();
		BabyName right = this.getRight();
		if (left != null) {
			leftheight = left.getHeight();
		}
		if (right != null) {
			rightheight = right.getHeight();
		}
		int height = Math.max(leftheight, rightheight) + 1;

		this.setHeight(height);
	}

	public void balance() {

		if (this.balanceFactor() == 2) {
			BabyName left = this.getLeft();
			if (left != null) {
				switch (left.balanceFactor()) {
				case -1:
					this.getLeft().rotateLeft();
				case 1:
					this.rotateRight();
					break;
				}
			}
		} else if (this.balanceFactor() == -2) {
			BabyName right = this.getRight();
			if (right != null) {
				switch (right.balanceFactor()) {
				case 1:
					this.getRight().rotateRight();
				case -1:
					this.rotateLeft();
					break;
				}
			}
		}
	}

	public void updateSize() {
		BabyName left_child = this.getLeft();
		BabyName right_child = this.getRight();
		
		int size = 1;
		
		if (left_child != null){
			size += left_child.getSize();
		}
		
		if (right_child != null){
			size += right_child.getSize();
		}
		
		this.setSize(size);
  }
	
	public void rotateRight() {
		BabyName left_pivot = this.getLeft();

		BabyName right_of_left_pivot = left_pivot.getRight();

		this.setLeft(right_of_left_pivot);

		if (right_of_left_pivot != null) {
			right_of_left_pivot.setParent(this);
		}

		left_pivot.setParent(this.getParent());

		this.setParent(left_pivot);
	}

	public void rotateLeft() {

		BabyName right_pivot = this.getRight();

		BabyName left_of_right_pivot = right_pivot.getLeft();

		this.setRight(left_of_right_pivot);

		if (left_of_right_pivot != null) {
			left_of_right_pivot.setParent(this);
		}

		right_pivot.setParent(this.getParent());

		this.setParent(right_pivot);

	}

}

class MaleBabyName extends BabyName {

	public MaleBabyName(String name) {
		super(name, Gender.MALE);
	}

}

class FemaleBabyName extends BabyName {
	public FemaleBabyName(String name) {
		super(name, Gender.FEMALE);
	}
}

interface ITree<T> {
	void insert(T element);
}

interface IOrderStatisticTree<T> {
	T select(int index);
	int rank(T element);
}

class BabyTree<T> implements ITree<BabyName>, IOrderStatisticTree<BabyName> {

	private BabyName _root;

	public BabyTree(Comparator<BabyName> babyname_comparator) {

	}

	int count_match(String start, String end) {
		return recursive_name_count(this._root, start, end);
	}

	int recursive_name_count(BabyName babyname, String start, String end) {
		if (babyname == null) {
			return 0;
		} else {

			int lexical_start_bound = babyname.getName().compareTo(start);
			int lexical_end_bound = babyname.getName().compareTo(end);

			if (lexical_start_bound >= 0 && lexical_end_bound < 0) {
				int left = recursive_name_count(babyname.getLeft(), start, end);
				int right = recursive_name_count(babyname.getRight(), start, end);
				return left + 1 + right;
			} else if (lexical_start_bound < 0) {
				int right = recursive_name_count(babyname.getRight(), start, end);
				return right;
			} else if (lexical_end_bound >= 0) {
				int left = recursive_name_count(babyname.getLeft(), start, end);
				return left;
			} else {
				return 0;
			}
		}
	}

	public void insert(BabyName bn) {
		BabyName _parent = null;
		BabyName _current = this._root;
		while (_current != null) {
			_parent = _current;
			if (bn.compareTo(_current) < 0) {
				_current = _current.getLeft();
			} else {
				_current = _current.getRight();
			}
		}

		bn.setParent(_parent);

		this.postInsert(bn);
	}

	/***
	 * AVL post insert procedure 1. update the height for each of the parent node
	 * 2. balance the subtree for each of the parent node.
	 * 
	 * @param bn
	 *          Node
	 */
	private void postInsert(BabyName bn) {
		BabyName _current = bn;
		while (_current != null) {
			_current.updateHeight();
			_current.balance();
			_current.updateSize();

			if (_current.getParent() == null) {
				this._root = _current;
			}

			_current = _current.getParent();

		}

	}

	public String getString() {
		if (this._root == null) {
			return "No Baby Names";
		} else {
			return this.getString(this._root, "", true);
		}
	}

	private String getString(BabyName node, String prefix, boolean isTail) {
		StringBuilder builder = new StringBuilder();

		if (node.getParent() != null) {
			String side = "left";
			if (node == node.getParent().getRight()) {
				side = "right";
			}
			builder.append(prefix + (isTail ? "|__ " : "|-- ") + "(" + side + ") "
			    + node.getName() + "\n");
		} else {
			builder.append(prefix + (isTail ? "|__ " : "|-- ") + node.getName()
			    + "\n");
		}
		List<BabyName> children = null;
		BabyName left = node.getLeft();
		BabyName right = node.getRight();
		if (left != null || right != null) {
			children = new ArrayList<BabyName>(2);
			if (left != null) {
				children.add(left);
			}
			if (right != null) {
				children.add(right);
			}
		}
		if (children != null) {
			for (int i = 0; i < children.size() - 1; i++) {
				builder.append(this.getString(children.get(i), prefix
				    + (isTail ? "    " : "|   "), false));
			}
			if (children.size() >= 1) {
				builder.append(this.getString(children.get(children.size() - 1), prefix
				    + (isTail ? "    " : "|   "), true));
			}
		}

		return builder.toString();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getString();
	}

	@Override
  public BabyName select(int index) {
		return this.select(this._root, index);
  }

	public BabyName select(BabyName babyname, int index) {
		int left_children_size = 0;
		if (babyname.getLeft() != null){
			left_children_size = babyname.getLeft().getSize();
		}
		
		if (index == left_children_size){
			return babyname;
		} else if (index < left_children_size){
			return this.select(babyname.getLeft(), index);
		} else {
			return this.select(babyname.getRight(), index - (left_children_size + 1));
		}
  }

	@Override
  public int rank(BabyName element) {
		
	  return 0;
  }
}

class BabyNames {

	// Declaration
	// --------------------------------------------
	private BabyTree<FemaleBabyName> _female_baby_names;
	private BabyTree<MaleBabyName> _male_baby_names;

	// --------------------------------------------

	public BabyNames() {
		Comparator<BabyName> babyname_comparator = new Comparator<BabyName>() {

			@Override
			public int compare(BabyName o1, BabyName o2) {
				return o1.compareTo(o2);
			}

		};
		_female_baby_names = new BabyTree<FemaleBabyName>(babyname_comparator);
		_male_baby_names = new BabyTree<MaleBabyName>(babyname_comparator);
	}

	private Gender getGender(int genderId) {
		Gender _gender;
		if (genderId == 1) {
			_gender = Gender.MALE;
		} else if (genderId == 2) {
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

		if (_gender == Gender.MALE) {
			this._male_baby_names.insert(bn);
		} else if (_gender == Gender.FEMALE) {
			this._female_baby_names.insert(bn);
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
			ans += this._male_baby_names.count_match(START, END);
		}

		if (gender_pref == Gender.FEMALE || gender_pref == null) {
			ans += this._female_baby_names.count_match(START, END);
		}
		// --------------------------------------------

		return ans;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return _male_baby_names + "\n" + _female_baby_names;
	}

	void run() throws Exception {
		// do not alter this method to avoid unnecessary errors with the automated
		// judging
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
		    System.out)));
		while (true) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int command = Integer.parseInt(st.nextToken());
			if (command == 0) // end of input
				break;
			else if (command == 1) // Add Suggestion
				AddSuggestion(st.nextToken(), Integer.parseInt(st.nextToken()));
			else
				// if (command == 2) // Query
				pr.println(Query(st.nextToken(), // START
				    st.nextToken(), // END
				    Integer.parseInt(st.nextToken()))); // GENDER
		}
		pr.close();
	}

	public static void main(String[] args) throws Exception {
		// do not alter this method to avoid unnecessary errors with the automated
		// judging
		BabyNames ps1 = new BabyNames();
		ps1.run();
	}
}
