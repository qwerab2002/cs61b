public class Planet {
    private final double G = 6.67e-11;
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double distance = Math.sqrt((this.xxPos - p.xxPos) * (this.xxPos - p.xxPos) +
                                    (this.yyPos - p.yyPos) * (this.yyPos - p.yyPos));
        return distance;
    }

    public double calcForceExertedBy(Planet p) {
        double F = G * this.mass * p.mass / (this.calcDistance(p) * this.calcDistance(p));
        return F;
    }

    public double calcForceExertedByX(Planet p) {
        double F = this.calcForceExertedBy(p);
        double distance = this.calcDistance(p);
        double dx = p.xxPos - this.xxPos;
        //if (dx < 0)
        //    dx = -dx;
        return F * dx / distance;
    }

    public double calcForceExertedByY(Planet p) {
        double F = this.calcForceExertedBy(p);
        double distance = this.calcDistance(p);
        double dy = p.yyPos - this.yyPos;
        //if (dy < 0)
        //    dy = -dy;
        return F * dy / distance;
    }

    public double calcNetForceExertedByX(Planet[] allPlanets){
        double Fx = 0;
        for (Planet p : allPlanets) {
            if (p != this) {
                //if (p.xxPos > this.xxPos) {
                    Fx += this.calcForceExertedByX(p);
                //} else {
                //    Fx -= this.calcForceExertedByX(p);
                //}
            }
        }
        return Fx;
    }

    public double calcNetForceExertedByY(Planet[] allPlanets){
        double Fy = 0;
        for (Planet p : allPlanets) {
            if (p != this) {
               // if (p.yyPos > this.yyPos) {
                    Fy += this.calcForceExertedByY(p);
                //} else {
                 //   Fy -= this.calcForceExertedByY(p);
               // }
            }
        }
        return Fy;
    }

    public void update(double dt, double Fx, double Fy){
        double ax = Fx / this.mass;
        double ay = Fy / this.mass;
        this.xxVel += dt * ax;
        this.yyVel += dt * ay;
        this.xxPos += dt * xxVel;
        this.yyPos += dt * yyVel;
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + imgFileName);
        StdDraw.show();
    }
}
