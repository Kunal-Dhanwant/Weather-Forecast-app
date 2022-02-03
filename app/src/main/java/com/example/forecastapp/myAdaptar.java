package com.example.forecastapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class myAdaptar extends RecyclerView.Adapter<myAdaptar.weatherHolder>{
    public myAdaptar(Context context, ArrayList<WeatherModel> weatherModelArrayList) {
        this.context = context;
        this.weatherModelArrayList = weatherModelArrayList;
    }

    private Context context;
    private ArrayList<WeatherModel> weatherModelArrayList;

    @NonNull
    @Override
    public weatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.weather_rv_item,parent,false);

        return new weatherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull weatherHolder holder, int position) {

        WeatherModel weather = weatherModelArrayList.get(position);

        holder.tempTV.setText(weather.getTemperature()+"°C");
        holder.windspeedTV.setText(weather.getWindspeed()+"kph");

        Glide.with(context).load("https:"+weather.getIcon()).into(holder.conditionIV);

        SimpleDateFormat input = new SimpleDateFormat("yyyy-mm-dd HH:mm");
        SimpleDateFormat output = new SimpleDateFormat("HH:mm aa");

        try{
            Date t = input.parse(weather.getTime());

            holder.timeTV.setText(output.format(t));

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return weatherModelArrayList.size();
    }

    public class weatherHolder extends RecyclerView.ViewHolder {
        TextView windspeedTV,timeTV,tempTV;
        ImageView conditionIV;

        public weatherHolder(@NonNull View itemView) {
            super(itemView);

            windspeedTV= itemView.findViewById(R.id.idTVwindspeed);
            timeTV = itemView.findViewById(R.id.idTVTime);
            tempTV = itemView.findViewById(R.id.idTVTemp);
            conditionIV= itemView.findViewById(R.id.idIVcondition);

        }
    }
}
