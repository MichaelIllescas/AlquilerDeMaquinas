package com.example.alquilerdemaquinas;


import java.text.DecimalFormat;

import com.example.alquilerdemaquinas.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView total;
	EditText nombre, dni, telefono, diasAlquiler;
	RadioButton compresor, sierra, hormigonera;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        nombre=(EditText)findViewById(R.id.nombre);
        dni=(EditText)findViewById(R.id.dni);
        telefono=(EditText)findViewById(R.id.telefono);
        diasAlquiler=(EditText)findViewById(R.id.diasAlquiler);
        
        total= (TextView)findViewById(R.id.total);
        
        compresor= (RadioButton)findViewById(R.id.compresor);
        sierra= (RadioButton)findViewById(R.id.sierraElectrica);
        hormigonera=(RadioButton)findViewById(R.id.homigonera);
        
        
        String name = nombre.getText().toString();
		String DNI= dni.getText().toString();
		String tel= telefono.getText().toString();
		String dias= diasAlquiler.getText().toString();
    }

    public String nombre(View v){
		 String name= nombre.getText().toString();
		 return name;
	 } 
    
    public String cantidadDias (View v){
		 String cantidad= diasAlquiler.getText().toString();
		 return cantidad;
	 } 
    
    public String telefono(View V){
    	String tel= telefono.getText().toString();
    	return tel;
    }
    
    public boolean esEntero(String dato) {
        if (dato.matches("-?\\d+")) {
            return true;
        } else {
            return false;
        }
    }
  
    public boolean esFloat(String dato) {
        try {
            Float.parseFloat(dato);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean esNumero(String valor) {
        try {
            Double.parseDouble(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
   
    public String importe(View v)
    {	

        String name = nombre.getText().toString();
		String DNI= dni.getText().toString();
		String tel= telefono.getText().toString();
		String dias= diasAlquiler.getText().toString();
    	
    
    	
    	DecimalFormat f= new DecimalFormat("0.00");
    	float importeTotal=0;
    	
    	if (name.equalsIgnoreCase("")|| DNI.equalsIgnoreCase("") || tel.equalsIgnoreCase("") || dias.equalsIgnoreCase("") )
		{
			Toast t= Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_LONG);
			t.show();
		}else
		{
			if( !esEntero(DNI) ||  !esEntero(tel) || !esEntero(dias) || esNumero(name) )
			{
				Toast t= Toast.makeText(this, "Valor Ingresado Invalido", Toast.LENGTH_SHORT);
				t.show();
			}else
			{
				int diasAlquilados= Integer.parseInt(dias);
				if(compresor.isChecked() )
				{
					
					importeTotal= diasAlquilados*3000;
					total.setText("$ "+f.format(importeTotal));
				}else
				{
					if (sierra.isChecked())
					{
						importeTotal= diasAlquilados*1500;
						total.setText("$ "+f.format(importeTotal));
					}else
					{
						if(hormigonera.isChecked())
						{
							importeTotal=diasAlquilados*2500;
							total.setText("$ "+f.format(importeTotal));
						}
					}
				}
			
			
			}
			
		}
		return String.valueOf(f.format(importeTotal));
    	
    }

    public void limpiar(View v)
    {
    	nombre.setText("");
    	dni.setText("");
    	telefono.setText("");
    	diasAlquiler.setText("");
    	total.setText("$0.00");
    	
    }
   
    public void alta(View v){
    	Administrador a= new Administrador(this, "comercio", null, 1);
    	SQLiteDatabase db=a.getReadableDatabase();
    	
    	String name= nombre.getText().toString();
    	String DNI= dni.getText().toString();
    	String tel= telefono.getText().toString();
    	String montoTotal= total.getText().toString();
    	
    	ContentValues registro= new ContentValues(); 
    	if (name.equalsIgnoreCase("")|| DNI.equalsIgnoreCase("") || tel.equalsIgnoreCase("") || montoTotal.equalsIgnoreCase("$0.00") )
		{
			Toast t= Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_LONG);
			t.show();
		}else
		{
    	
    	registro.put("dni", DNI);
    	registro.put("nombre", name);
    	registro.put("telefono", tel);
    	registro.put("importeAlquilado", montoTotal);
    	
    	
    	db.insert("Alquileres", null, registro);
    	
    	db.close();
    	
    	Toast toast= Toast.makeText(this, "El alquiler se guardo correctamente.", Toast.LENGTH_SHORT); 
    	toast.show();
    	limpiar(v);
		}
    	
    		
    }
    
    public void consulta(View v){
    	
    	Administrador a= new Administrador(this, "comercio", null, 1);
    	SQLiteDatabase db=a.getReadableDatabase();
    
    	String dnix= dni.getText().toString();
    	if (dnix.equalsIgnoreCase("") )
		{
			Toast t= Toast.makeText(this, "Debe completar campo DNI", Toast.LENGTH_LONG);
			t.show();
		}else
		{
    	String sql= "Select dni, nombre, telefono, importeAlquilado from Alquileres where dni='"+dnix+"' ";
    	
    	Cursor fila= db.rawQuery(sql, null);
    	if(fila.moveToFirst())
    	{
    		nombre.setText(fila.getString(1));
    		dni.setText(fila.getString(0));
    		telefono.setText(fila.getString(2));
    		total.setText(fila.getString(3));
    	}else{
    		Toast toast= Toast.makeText(this, "El cliente con DNI "+ dnix + " no se encontró registrado.", Toast.LENGTH_SHORT); 
        	toast.show();
    	}
    	db.close();
		}
    }
    
    public void factura(View v){
    	Intent i= new Intent(this, Factura.class);
		i.putExtra("nombre", nombre(v));
		i.putExtra("telefono", telefono(v));
		i.putExtra("dias", cantidadDias(v));
		i.putExtra("importe", importe(v));
		
		if (nombre(v).equalsIgnoreCase("")|| telefono(v).equalsIgnoreCase("") || importe(v).equalsIgnoreCase("$0.00") || cantidadDias(v).equalsIgnoreCase("") )
		{
			Toast t= Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_LONG);
			t.show();
		}else
		{
		startActivity(i);
		}
	}
    	
    	
   }
    
    
  
