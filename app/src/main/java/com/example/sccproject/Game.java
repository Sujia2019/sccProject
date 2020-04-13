package com.example.sccproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Random;
import java.util.Stack;


public class Game extends AppCompatActivity {
    public int widths;
    public Button btn_top, btn_down, btn_left, btn_right, btn_play, btn_pause;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        DisplayMetrics metric = new DisplayMetrics();//获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(metric);//获取屏幕信息
        widths = metric.widthPixels;// 屏幕宽度（像素）
        final FrameLayout frameLayout = (FrameLayout)findViewById(R.id.frameLayout);
        btn_top = (Button)findViewById(R.id.top);
        btn_down = (Button)findViewById(R.id.down);
        btn_left = (Button)findViewById(R.id.left);
        btn_right = (Button)findViewById(R.id.right);
        btn_play = (Button)findViewById(R.id.btn_play);
        btn_pause = (Button)findViewById(R.id.btn_pause);
        final MyView myView = new MyView(this);
        frameLayout.addView(myView);

        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.move(1);
                myView.postInvalidate();
                myView.checkIsWin();
            }
        });
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.move(2);
                myView.postInvalidate();
                myView.checkIsWin();
            }
        });
        btn_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.move(3);
                myView.postInvalidate();
                myView.checkIsWin();
            }
        });
        btn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.move(4);
                myView.postInvalidate();
                myView.checkIsWin();
            }
        });
        final MediaPlayer mMediaPlayer = MediaPlayer.create(Game.this, R.raw.background_music);
        mMediaPlayer.setLooping(true);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mMediaPlayer.isPlaying()) {
                    mMediaPlayer.start();
                    mMediaPlayer.isLooping();
                }
            }
        });
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                }
            }
        });
    }

    public class MyView extends View {
        public int NUM = 10;     //格子数，调整难度 [num][num]
        public int padding = 10, width = (widths -2*padding)/NUM;// width 每个格子的宽度和高度，padding内边距
        public Lattice[][] maze;//格子的二位数组地图
        public int ballX, ballY;//玩家的位置
        public boolean drawPath = false;

        public Paint paint;
        public MyView(Context context) {
            super(context);
            init();
        }
        public void init() {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(0xFF000000);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(5);

            maze = new Lattice[NUM][NUM];//地图的大小
            //对格子初始化
            for (int i = 0; i <= NUM - 1; i++)
                for (int j = 0; j <= NUM - 1; j++)
                    maze[i][j] = new Lattice(i, j);
            for (int i = 0; i <= NUM - 1; i++)
                for (int j = 0; j <= NUM - 1; j++) {
                    maze[i][j].setFather(null);
                    maze[i][j].setFlag(Lattice.NOTINTREE);
                }
            ballX = 0;
            ballY = 0;
            drawPath = false;
            createMaze();
        }

        public void createMaze() {
            Random random = new Random();
            int rx = Math.abs(random.nextInt()) % NUM;
            int ry = Math.abs(random.nextInt()) % NUM;
            Stack<Lattice> s = new Stack<Lattice>();//此处使用了BFS思想,此时使用的Stack
            Lattice p = maze[rx][ry];	//先随机确定一个点，然后利用广度优先搜索的思想对整个图进行遍历，转换成树
            Lattice neis[] = null;	//网格
            s.push(p);
            while (!s.isEmpty()) {
                p = s.pop();
                p.setFlag(Lattice.INTREE);
                neis = getNeis(p);
                int ran = Math.abs(random.nextInt()) % 4;
                for (int a = 0; a <= 3; a++) {
                    ran++;
                    ran %= 4;
                    if (neis[ran] == null || neis[ran].getFlag() == Lattice.INTREE)	//对周边的结点遍历，存在且未访问过
                        continue;
                    s.push(neis[ran]);
                    neis[ran].setFather(p);//记录周围的子节点
                }
            }
        }
        public Lattice[] getNeis(Lattice p) {
            final int[] adds = {-1, 0, 1, 0, -1};
            if (isOutOfBorder(p)) {
                return null;
            }
            Lattice[] ps = new Lattice[4];
            int xt;
            int yt;
            for (int i = 0; i <= 3; i++) {
                xt = p.getX() + adds[i];
                yt = p.getY() + adds[i + 1];
                if (isOutOfBorder(xt, yt))
                    continue;
                ps[i] = maze[xt][yt];
            }
            return ps;
        }
        public void move(int c) {
            int tx = ballX, ty = ballY;
            switch (c) {
                case 1 :
                    ty--;
                    break;
                case 2 :
                    ty++;
                    break;
                case 3 :
                    tx--;
                    break;
                case 4 :
                    tx++;
                    break;
                case 5 :
                    if (drawPath == true) {
                        drawPath = false;
                    } else {
                        drawPath = true;
                    }
                    break;
                default :
            }
            //检查是否联通且未越界
            if (!isOutOfBorder(tx, ty) && (maze[tx][ty].getFather() == maze[ballX][ballY]
                    || maze[ballX][ballY].getFather() == maze[tx][ty])) {
                ballX = tx;
                ballY = ty;
            }
        }
        public boolean isOutOfBorder(Lattice p) {
            return isOutOfBorder(p.getX(), p.getY());
        }
        public boolean isOutOfBorder(int x, int y) {
            return (x > NUM - 1 || y > NUM - 1 || x < 0 || y < 0) ? true : false;
        }

        public int getCenterX(int x) {
            return padding + x * width + width / 2;
        }
        public int getCenterY(int y) {
            return padding + y * width + width / 2;
        }
//        public int getCenterX(com.example.lee.maze.Lattice p) {
//            return padding + p.getY() * width + width / 2;
//        }
//        public int getCenterY(com.example.lee.maze.Lattice p) {
//            return padding + p.getX() * width + width / 2;
//        }
        private void checkIsWin() {
            if (ballX == NUM - 1 && ballY == NUM - 1) {
                Toast.makeText(Game.this,"YOU WIN", Toast.LENGTH_SHORT).show();
                init();
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawColor(0xFFffffff);
//            canvas.
            //从上到下画线
            for (int i = 0; i <= NUM; i++) {
                canvas.drawLine(padding + i * width, padding, padding + i * width,
                        padding + NUM * width, paint);
            }
            //从左到右画线
            for (int j = 0; j <= NUM; j++) {
                canvas.drawLine(padding, padding + j * width, padding + NUM * width,
                        padding + j * width, paint);
            }
//        g.setColor(this.getBackground());

            paint.setColor(0xFFffffff);

            for (int i = NUM - 1; i >= 0; i--) {
                for (int j = NUM - 1; j >= 0; j--) {
                    Lattice f = maze[i][j].getFather();
                    if (f != null) {
                        int fx = f.getX(), fy = f.getY();	//获取父节点的坐标
                        clearFence(i, j, fx, fy, canvas);
                    }
                }
            }
            //入口
            canvas.drawLine(padding, padding + 1, padding, padding + width - 1, paint);
            int last = padding + NUM * width;
            //出口
            canvas.drawLine(last, last - 1, last, last - width + 1, paint);

            //画玩家，及小球
            paint.setColor(0xFFff0000);
            paint.setStyle(Paint.Style.FILL);
            float cx = getCenterX(ballY) - width / 3 + padding;
            float cy = getCenterY(ballX) - width / 3 + padding;
            float ra = width / 2;
            canvas.drawCircle(cx, cy, ra, paint);
        }
        public void clearFence(int i, int j, int fx, int fy, Canvas canvas) {
            int sx = padding + ((j > fy ? j : fy) * width),//取出两个点中相对于右下角的点的坐标
                    sy = padding + ((i > fx ? i : fx) * width),
                    dx = (i == fx ? sx : sx + width),//如果和父结点在同一列,取右下角的x;不在，右下角x + "1"加上一个单位长度
                    dy = (i == fx ? sy + width : sy);//...                        y + "1"           y   (右移还是下移)
            if (sx != dx) {
                sx++;
                dx--;
            } else {
                sy++;
                dy--;
            }
            canvas.drawLine(sx, sy, dx, dy, paint);
        }
    }
}

