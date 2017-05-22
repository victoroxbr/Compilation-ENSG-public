// init algo
algo.hide();
algo.setTextSize(50);
set LETTER_SPACE = 75;
set TEXT_Y = algo.getTop() - 50;

// init text
set drawFormula = function (text, focus) {
	
	set x = - (text.length() * LETTER_SPACE) / 2;
	
	for (set i = 0; i < text.length(); i++) {
		set c = if (i == focus) algo.color.CYAN else algo.color.WHITE;
		algo.setColor(c);
		algo.goTo(x, TEXT_Y);
		algo.text(text.getChar(i));
		x += LETTER_SPACE;
	}
}

drawFormula("7*(5+3)", 1);

set graph = object () {
	set node = object () {
		
		set x;
		set y;
		set text;
		set isVisible;
		set isFocused;
		
		set draw = function () {
			if (!isVisible) return;
			set c = if (isFocused) algo.color.CYAN else algo.color.WHITE;
			algo.setColor(c);
			algo.goTo(x, y);
			algo.disc(150);
			algo.setRGB(81,77,74);
			algo.disc(125);
			algo.setColor(c);
			algo.goTo(x-4, y-8);
			algo.text(text);
		};
		
	};
	
	set arc = object() {
		
		set id1;
		set id2;
		
		set draw = function () {
			set node1 = graph.nodes[id1];
			set node2 = graph.nodes[id2];
			
			if (!node1.isVisible || !node2.isVisible) return;
			algo.setColor(algo.color.WHITE);
			algo.setStroke(10);
			algo.goTo(node1.x, node1.y);
			algo.lineTo(node2.x, node2.y);
		};
		
	};
	
	set nodes = array() {
		node.clone(-150, 50, "7", true, false),
		node.clone(0, -150, "*", true, false),
		node.clone(0, 250, "5", true, false),
		node.clone(150, 50, "+", true, false),
		node.clone(300, 250, "3", true, false)
	};
	
	set arcs = array() {
		arc.clone(1, 0),
		arc.clone(1, 3),
		arc.clone(3, 2),
		arc.clone(3, 4)
	};
	
	set setVisible = function (id) {
		nodes[id].isVisible = true;
	};

	set setFocus = function (id) {
		nodes.each ((item) { item.isFocus = false; });
		nodes[id].isFocus = true;
	};
	
	set draw = function () {
		arcs.each((item) { item.draw() });
		nodes.each((item) { item.draw() });
	};
	
};

graph.draw();
