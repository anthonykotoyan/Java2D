public class vec2 {
    public float x;
    public float y;
    public vec2(float x, float y){
        this.x = x;
        this.y = y;
    }

    public vec2 add(float x, float y){
        return new vec2(this.x+x,this.y+y);
    }

    public vec2 add(vec2 v){
        return new vec2(this.x + v.x, this.y + v.y);
    }

    public vec2 subtract(float x, float y){
        return new vec2(this.x - x, this.y - y);
    }

    public vec2 subtract(vec2 v){
        return new vec2(this.x - v.x, this.y - v.y);
    }

    public vec2 multiply(float s){
        return new vec2(this.x * s, this.y * s);
    }

    public vec2 divide(float s){
        return new vec2(this.x / s, this.y / s);
    }

    public float dot(vec2 v){
        return this.x * v.x + this.y * v.y;
    }

    public float magnitude(){
        return (float) Math.sqrt(x * x + y * y);
    }

    public vec2 normalize(){
        float mag = magnitude();
        if (mag == 0) return new vec2(0, 0);
        return divide(mag);
    }

    public float distance(vec2 v){
        float dx = this.x - v.x;
        float dy = this.y - v.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public float angle(){
        return (float) Math.atan2(y, x);
    }

    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void set(vec2 v){
        this.x = v.x;
        this.y = v.y;
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }

}
