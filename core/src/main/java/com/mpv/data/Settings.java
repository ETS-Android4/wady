package com.mpv.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Locale;

import com.badlogic.gdx.Gdx;

public class Settings {
    public static boolean soundEnabled = true;
    public static boolean musicEnabled = true;
    public final static Integer[] highscores = new Integer[]{10000, 9000, 8000, 7000, 6000};
    public final static String[] scorenames = new String[]{"Cartman", "Kyle", "Kenny", "Stan", "Butters"};
    public final static Integer[] points = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public final static Integer[] stars = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public final static String file = ".batcho";
    public static String name = "Player";

    public static void load() {
        String tmp;
        try(BufferedReader in = new BufferedReader(new InputStreamReader(Gdx.files.local(file).read()))) {
            name = in.readLine();
            soundEnabled = Boolean.parseBoolean(in.readLine());
            musicEnabled = Boolean.parseBoolean(in.readLine());
            for (int i = 0; i < 5; i++) {
                highscores[i] = Integer.parseInt(in.readLine());
                scorenames[i] = in.readLine();
            }
            for (int i = 0; i < 16; i++) {
                tmp = in.readLine();
                points[i] = Integer.parseInt(tmp.substring(0, 5));
                stars[i] = Integer.parseInt(tmp.substring(6, 7));
            }
        } catch (Throwable e) {
            // :( It's ok we have defaults
        }
    }

    public static void save() {
        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(Gdx.files.local(file).write(false)))) {
            out.write(name.concat("\n"));
            out.write(Boolean.toString(soundEnabled).concat("\n"));
            out.write(Boolean.toString(musicEnabled).concat("\n"));
            for (int i = 0; i < 5; i++) {
                out.write(Integer.toString(highscores[i]).concat("\n"));
                out.write(scorenames[i].concat("\n"));
            }
            for (int i = 0; i < 16; i++) {
                out.write(String.format(Locale.US, "%05d", points[i]).concat(" "));
                out.write(Integer.toString(stars[i]).concat("\n"));
            }

        } catch (Throwable e) {
            System.out.println("Unable to save scores!");
        }
    }

    public static void addScore(String name, int score) {
        for (int i = 0; i < 5; i++) {
            if (highscores[i] == score && scorenames[i].equals(name))
                return;
        }
        for (int i = 0; i < 5; i++) {
            if (highscores[i] < score) {
                for (int j = 4; j > i; j--) {
                    highscores[j] = highscores[j - 1];
                    scorenames[j] = scorenames[j - 1];
                }
                highscores[i] = score;
                scorenames[i] = name;
                break;
            }
        }
    }

    public static int getTotalScore() {
        int score = 0;
        for (int i = 0; i < 16; i++) {
            score += points[i];
        }
        addScore(name, score);
        return score;
    }
}