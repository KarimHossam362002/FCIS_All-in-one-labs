
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class SecondScreen extends StatelessWidget{
  final String name;
  final String genre;

  const SecondScreen({super.key, required this.name,required this.genre});
  @override
  Widget build(BuildContext context){
    return Scaffold(
      appBar: AppBar(
        title: const Text("Book screen"),
      ),
      body: Center(
        child: Text(name + " " + genre ),

      ),
    );
  }
}