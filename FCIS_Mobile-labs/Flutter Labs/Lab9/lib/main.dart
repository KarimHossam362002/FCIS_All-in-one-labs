import 'package:flutter/material.dart';
import 'bookDetails.dart';
void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // TRY THIS: Try running your application with "flutter run". You'll see
        // the application has a purple toolbar. Then, without quitting the app,
        // try changing the seedColor in the colorScheme below to Colors.green
        // and then invoke "hot reload" (save your changes or press the "hot
        // reload" button in a Flutter-supported IDE, or press "r" if you used
        // the command line to start the app).
        //
        // Notice that the counter didn't reset back to zero; the application
        // state is not lost during the reload. To reset the state, use hot
        // restart instead.
        //
        // This works for code too, not just values: Most code changes can be
        // tested with just a hot reload.
        colorScheme: .fromSeed(seedColor: Colors.deepPurple),
      ),
      home: const MyHomePage(title: 'Library (0 Books)'), // HERE THE TITLE OF THE PROJECT
    );
  }
}
final List<String> _genres = [
  'Fiction',
  'Non-fiction',
  'Science',
  'Biography',
  'Fantasy',
  'Mystery'
];
class Book {
   String name;
   String genre;

  Book({required this.name,required this.genre});
}
class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;


  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final TextEditingController _textController = TextEditingController();

  final List<Book> _books = [];
  String _selectedGenre = "";
  void addBook(){
    String inputText = _textController.text.trim();
    if(inputText.isNotEmpty){
      setState(() {
        _books.add(new Book(name: inputText,genre: _selectedGenre));
      });
      _textController.clear();
    }
  }
  void deleteBook(int index){
    _books.removeAt(index);
  }

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // TRY THIS: Try changing the color here to a specific color (to
        // Colors.amber, perhaps?) and trigger a hot reload to see the AppBar
        // change color while the other colors stay the same.
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
      ),
      body: Center(

        child: Column(
          children: [
            Row(
              children: [
                Expanded(
                    child: TextField(
                  controller: _textController,
                  decoration: const InputDecoration(
                    labelText: 'Enter Book',
                    border: OutlineInputBorder(),
                  ),

                )
                ),
                const SizedBox(width: 8),
                DropdownButton(value: _selectedGenre,
                    hint: const Text("Select Genre"),
                    items: _genres.map((genre)=>DropdownMenuItem(value: _selectedGenre,child: Text("genre"), )).toList()
                    ,
                    onChanged: (value){
                      setState(() {
                        if(value != null)
                        _selectedGenre= value;
                      });
                    }),
                const SizedBox(width: 8),
                ElevatedButton(onPressed: addBook, child: const Text('Add'))
              ],
            ),
            Row(
              children: [
                Expanded(child: ListView.builder(
                  itemCount: _books.length,
                  itemBuilder: (context,index){
                    return Card(
                      margin: const EdgeInsets.symmetric(vertical: 4),
                      child: ListTile(
                        title: Text(_books[index].name),
                        onTap: (){
                          //HERE INTENT TO bookDetails.dart
                          Navigator.push(context, MaterialPageRoute(builder: (context)=> SecondScreen(
                            name:_books[index].name,
                            genre:_books[index].genre
                          )
                          )
                          );
                        },
                        onLongPress:()=> deleteBook(index) ,
                      ),
                    );
                  },
                ))
              ],
            ),
          ],


        ),



      ),
    );
  }
}
